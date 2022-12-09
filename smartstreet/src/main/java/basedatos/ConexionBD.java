package basedatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import java.lang.NullPointerException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import logica.Log;

public class ConexionBD {

    Connection conexion;

    public Connection crearConexion(boolean autoCommit) throws NullPointerException
    {
        Connection con = null;
        int intentos = 3;
        for (int i = 0; i < intentos; i++) 
        {
        	Log.logbasedatos.info("Intento {} de conexion a la Base de Datos.", i);
        	try
	          {
	            Context ctx = new InitialContext();
	            // Get the connection factory configured in Tomcat (Cambiar la direccion)
	            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ubicomp");

	            // Obtiene una conexion
	            con = ds.getConnection();
				Calendar calendar = Calendar.getInstance();
				java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
	            Log.logbasedatos.debug("Creacion de conexion. Identidficador de conexion {} || {}", con.toString(), date.toString());
	            con.setAutoCommit(autoCommit);
	        	Log.logbasedatos.info("Conexion establecida en el intento: " + i);
	            i = intentos;
	          } catch (NamingException ex)
	          {
	            Log.logbasedatos.error("ERROR intentando establecer conexion a la Base de Datos: {} => {}", i, ex); 
	          } catch (SQLException ex)
	          {
	            Log.logbasedatos.error("ERROR intentando conectar con SQL: {} => {}", i, ex);
	            throw (new NullPointerException("Conexion a SQL = Null"));
	          }
		}        
        return con;
    }

    public void cerrarTransaccion(Connection con) {
        try
          {
            con.commit();
            Log.logbasedatos.debug("Transaccion cerrada correctamente.");
          } catch (SQLException ex)
          {
            Log.logbasedatos.error("ERROR al cerrar la transaccion: {}", ex);
          }
    }
    
    public void cancelarTransaccion(Connection con)
    {
        try
          {
            con.rollback();
            Log.logbasedatos.debug("Transaccion cancelada correctamente.");
          } catch (SQLException ex)
          {
            Log.logbasedatos.error("ERROR de SQL al cancelar la transaccion: {}", ex);
          }
    }

    public void cerrarConexion(Connection con)
    {
        try
          {
        	Log.logbasedatos.info("Closing the connection");
            if (null != con)
            {
              Calendar calendar = Calendar.getInstance();
              java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
              Log.logbasedatos.debug("Connection closed. Bd connection identifier: {} obtained in {}", con.toString(), date.toString());
              con.close();
            }

        	Log.logbasedatos.info("The connection has been closed");
          } catch (SQLException e)
          {
        	  Log.logbasedatos.error("ERROR sql closing the connection: {}", e);
        	  e.printStackTrace();
          }
    }

    public static PreparedStatement getStatement(Connection con, String query){
        PreparedStatement pstatament = null;
        try {
            pstatament = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            Log.logbasedatos.warn("ERROR sql creating PreparedStatement:{} ", e);
        }
        return pstatament;
    }

    //**********LLAMADAS A BASE DE DATOS**********//
    //Extraer datos de tablas
    public static PreparedStatement GetCiudades(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Ciudad\"");
    }

    public static PreparedStatement GetZona(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Zona\" WHERE \"ID_Zona\"=? AND \"Codigo_Ciudad\"=?");
    }

    public static PreparedStatement GetZonasCiudad(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Zona\" WHERE \"Codigo_Ciudad\"=?");
    }
    
    public static PreparedStatement GetCalle(Connection con) {
        return getStatement(con, "SELECT \"Nombre\" FROM public.\"Calle\" WHERE \"Nombre\"=? AND \"ID_Zona\"=? AND \"Codigo_Ciudad_Zona\"=?");
    }
    
    public static PreparedStatement GetCallesZona(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Calle\" WHERE \"ID_Zona\"=? AND \"Codigo_Ciudad_Zona\"=?");
    }

    public static PreparedStatement GetSensoresCalle(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Sensor\" WHERE \"Codigo_Ciudad_Zona_Calle\"=? AND \"ID_Zona_Calle\"=? AND \"Nombre_Calle\"=?");
    }

    public static PreparedStatement GetSensoresCalleTipo(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Sensor\" WHERE \"Codigo_Ciudad_Zona_Calle\"=? AND \"ID_Zona_Calle\"=? AND \"Nombre_Calle\"=? AND \"Tipo\"=?");
    }
 
    public static PreparedStatement GetRegistrosCalle(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Registro\" WHERE \"Codigo_Ciudad_Zona_Calle\"=? AND \"ID_Zona_Calle\"=? AND \"Nombre_Calle\"=?");
    }

    public static PreparedStatement GetRegistrosCalleTipo(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Registro\" WHERE \"Codigo_Ciudad_Zona_Calle\"=? AND \"ID_Zona_Calle\"=? AND \"Nombre_Calle\"=? AND \"Tipo\"=?");
    }

    public static PreparedStatement GetHorasPuntaCalle(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"HorasPunta\" WHERE \"Codigo_Ciudad_Zona_Calle\"=? AND \"ID_Zona_Calle\"=? AND \"Nombre_Calle\"=?");
    }

    public static PreparedStatement GetNumMediciones(Connection con) {
        return getStatement(con, "SELECT COUNT(*) as NumMediciones FROM public.\"Sensor\"");
    }
    
    //Insertar datos en tablas
    public static PreparedStatement InsertarCiudad(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Ciudad\"(\"Codigo\", \"Nombre\", \"Pais\") VALUES (?, ?, ?)");
    }

    public static PreparedStatement InsertarZona(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Zona\"(\"Codigo_Ciudad\", \"ID\", \"Nombre\") VALUES (?, ?, ?)");
    }

    public static PreparedStatement InsertarCalle(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Calle\"(\"Codigo_Ciudad_Zona\", \"ID_Zona\", \"Nombre\") VALUES (?, ?, ?)");
    }

    public static PreparedStatement InsertarSensor(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Sensor\"(\"Codigo_Ciudad_Zona_Calle\", \"ID_Zona_Calle\", \"Nombre_Calle\", \"Tipo\", \"MarcaTemporal\", \"UnidadMedida\", \"Valor\") VALUES (?, ?, ?, ?, ?, ?, ?)");
    }

    public static PreparedStatement InsertarRegistro(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Registro\"(\"Codigo_Ciudad_Zona_Calle\", \"ID_Zona_Calle\", \"Nombre_Calle\", \"Tipo\", \"MarcaTemporal\", \"UnidadMedida\", \"Valor\") VALUES (?, ?, ?, ?, ?, ?, ?)");
    }

    public static PreparedStatement InsertarHorasPunta(Connection con) {
        return getStatement(con, "INSERT INTO public.\"HorasPunta\"(\"Codigo_Ciudad_Zona_Calle\", \"ID_Zona_Calle\", \"Nombre_Calle\", \"HoraInicio\", \"HoraFin\") VALUES (?, ?, ?, ?, ?)");
    }

    //Actualizar datos en tablas
    public static PreparedStatement ActualizarCalle(Connection con) {
        return getStatement(con, "UPDATE public.\"Calle\" SET \"Nombre\"=? WHERE \"Codigo_Ciudad_Zona\"=? AND \"ID_Zona\"=? AND \"Nombre\"=?");
    }

    public static PreparedStatement ActualizarSensor(Connection con) {
        return getStatement(con, "UPDATE public.\"Sensor\" SET \"MarcaTemporal\"=?, \"UnidadMedida\"=?, \"Valor\"=? WHERE \"Codigo_Ciudad_Zona_Calle\"=? AND \"ID_Zona_Calle\"=? AND \"Nombre_Calle\"=? AND \"Tipo\"=?");
    }

    public static PreparedStatement ActualizarRegistro(Connection con) {
        return getStatement(con, "UPDATE public.\"Registro\" SET \"MarcaTemporal\"=?, \"UnidadMedida\"=?, \"Valor\"=? WHERE \"Codigo_Ciudad_Zona_Calle\"=? AND \"ID_Zona_Calle\"=? AND \"Nombre_Calle\"=? AND \"Tipo\"=?");
    }

    public static PreparedStatement ActualizarHorasPunta(Connection con) {
        return getStatement(con, "UPDATE public.\"HorasPunta\" SET \"HoraInicio\"=?, \"HoraFin\"=? WHERE \"Codigo_Ciudad_Zona_Calle\"=? AND \"ID_Zona_Calle\"=? AND \"Nombre_Calle\"=?");
    }

    //Eliminar datos en tablas
    public static PreparedStatement EliminarCiudad(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Ciudad\" WHERE \"Codigo\"=?");
    }

    public static PreparedStatement EliminarZona(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Zona\" WHERE \"Codigo_Ciudad\"=? AND \"ID\"=?");
    }

    public static PreparedStatement EliminarCalle(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Calle\" WHERE \"Codigo_Ciudad_Zona\"=? AND \"ID_Zona\"=? AND \"Nombre\"=?");
    }

    public static PreparedStatement EliminarSensor(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Sensor\" WHERE \"Codigo_Ciudad_Zona_Calle\"=? AND \"ID_Zona_Calle\"=? AND \"Nombre_Calle\"=? AND \"Tipo\"=?");
    }

    public static PreparedStatement EliminarRegistro(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Registro\" WHERE \"Codigo_Ciudad_Zona_Calle\"=? AND \"ID_Zona_Calle\"=? AND \"Nombre_Calle\"=? AND \"Tipo\"=?");
    }

    public static PreparedStatement EliminarHorasPunta(Connection con) {
        return getStatement(con, "DELETE FROM public.\"HorasPunta\" WHERE \"Codigo_Ciudad_Zona_Calle\"=? AND \"ID_Zona_Calle\"=? AND \"Nombre_Calle\"=?");
    }
}

