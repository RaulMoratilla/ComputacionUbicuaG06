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

    public static PreparedStatement GetZonasDeCiudad(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Zona\" WHERE \"Codigo_Ciudad\"=?");
    }

    public static PreparedStatement GetZonas(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Zona\"");
    }

    public static PreparedStatement GetCallesDeZona(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Calle\" WHERE \"ID_Zona\"=? AND \"Codigo_Ciudad_Zona\"=?");
    }

    public static PreparedStatement GetCallesDeCiudad(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Calle\" WHERE \"Codigo_Ciudad_Zona\"=?");
    }

    public static PreparedStatement GetCalles(Connection con) {
        return getStatement(con, "SELECT \"Nombre\" FROM public.\"Calle\"");
    }

    public static PreparedStatement GetCalle(Connection con) {
        return getStatement(con, "SELECT \"Nombre\" FROM public.\"Calle\" WHERE \"Nombre\"=? AND \"ID_Zona\"=? AND \"Codigo_Ciudad_Zona\"=?");
    }

    public static PreparedStatement GetInfraDeCalle(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Sensor_Infrarrojo\" WHERE \"Nombre_Calle_Paso_Peatones\"=?");
    }

    public static PreparedStatement GetSInfrarrojo(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Sensor_Infrarrojo\"");
    }

    public static PreparedStatement GetSTemperaturaDeCalle(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Sensor_Temperatura\" WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement GetSTemperatura(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Sensor_Temperatura\"");
    }

    public static PreparedStatement GetSMovimientoDeCalle(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Sensor_Movimiento\" WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement GetSMovimiento(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Sensor_Movimiento\"");
    }

    public static PreparedStatement GetSLluviaDeCalle(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Sensor_Lluvia\" WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement GetSLluvia(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Sensor_Lluvia\"");
    }

    public static PreparedStatement GetSLuzDeCalle(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Sensor_Luz\" WHERE \"Nombre_Calle_Farola\"=?");
    }

    public static PreparedStatement GetSLuz(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Sensor_Luz\"");
    }
    
    public static PreparedStatement GetMediciones(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Medicion\"");
    }

    /*
    public static PreparedStatement GetMedicionTipo(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Medicion\" WHERE \"Tipo_Sensor\"=?");
    }

    public static PreparedStatement GetMedicionSensor(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Medicion\" WHERE \"Id_Sensor\"=? AND \"Tipo_Sensor\"=?");
    }
    
    public static PreparedStatement GetMedicionCalle(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Medicion\" natural inner join public.\"Sensor\" WHERE \"Id_Sensor\"=? AND \"Tipo_Sensor\"=?");
    }*/

    public static PreparedStatement GetHoraPunta(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Hora_Punta\" WHERE \"Nombre_Calle\"=?");
    }

    //Insertar datos en tablas
    public static PreparedStatement InsertarCalle(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Calle\"(\"Nombre\", \"ID_Zona\", \"Codigo_Ciudad_Zona\") VALUES (?, ?, ?)");
    }

    public static PreparedStatement InsertarCiudad(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Ciudad\"(\"Nombre\", \"Pais\", \"Codigo\") VALUES (?, ?, ?)");
    }

    public static PreparedStatement InsertarFarola(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Farola\"(\"Nombre_Calle\", \"Id\",  \"ID_Zona_Calle\", \"Codigo_Ciudad_Zona_Calle\", \"Altura\") VALUES (?, ?, ?, ?, ?)");
    }

    public static PreparedStatement InsertarHoraPunta(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Hora_Punta\"(\"HoraInicio\", \"HoraFin\", \"Nombre_Calle\", \"ID_Zona_Calle\", \"Codigo_Ciudad_Zona_Calle\") VALUES (?, ?, ?, ?, ?)");
    }

    public static PreparedStatement InsertarMedicion(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Medicion\"(\"Valor\", \"MarcaTemporal\", \"Id_sensor\", \"Tipo_Sensor\") VALUES (?, ?, ?, ?, ?, ?, ?)");
    }

    public static PreparedStatement InsertarPasoPeatones(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Paso_Peatones\"(\"Nombre_Calle\", \"Id\", \"ID_Zona_Calle\", \"Codigo_Ciudad_Zona_Calle\", \"Longitud\") VALUES (?, ?, ?, ?, ?)");
    }

    public static PreparedStatement InsertarSensorInfrarrojo(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Sensor_Infrarrojo\"(\"Id\", \"Unidad_Medida\", \"Id_Paso_Peatones\", \"Nombre_Calle_Paso_Peatones\", \"ID_Zona_Calle_Paso_Peatones\", \"Codigo_Ciudad_Zona_Calle_Paso_Peatones\") VALUES (?, ?, ?, ?, ?, ?)");
    }

    public static PreparedStatement InsertarSensorLluvia(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Sensor_Lluvia\"(\"Id\", \"UnidadMedida\", \"Nombre_Calle\", \"ID_Zona_Calle\", \"Codigo_Ciudad_Zona_Calle\") VALUES (?, ?, ?, ?, ?)");
    }

    public static PreparedStatement InsertarSensorLuz(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Sensor_Luz\"(\"Id\", \"UnidadMedida\", \"Id_Farola\", \"Nombre_Calle_Farola\", \"ID_Zona_Calle_Farola\", \"Codigo_Ciudad_Zona_Calle_Farola\") VALUES (?, ?, ?, ?, ?, ?)");
    }

    public static PreparedStatement InsertarSensorMovimiento(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Sensor_Movimiento\"(\"Id\", \"UnidadMedida\", \"Nombre_Calle\", \"ID_Zona_Calle\", \"Codigo_Ciudad_Zona_Calle\") VALUES (?, ?, ?, ?, ?)");
    }

    public static PreparedStatement InsertarSensorTemperatura(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Sensor_Temperatura\"(\"Id\", \"UnidadMedida\", \"Nombre_Calle\", \"ID_Zona_Calle\", \"Codigo_Ciudad_Zona_Calle\") VALUES (?, ?, ?, ?, ?)");
    }

    public static PreparedStatement InsertarZona(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Zona\"(\"ID\", \"Nombre\", \"Codigo_Ciudad\") VALUES (?, ?)");
    }

    //Actualizar datos en todas las tablas
    public static PreparedStatement ActualizarCalle(Connection con) {
        return getStatement(con, "UPDATE public.\"Calle\" SET \"Nombre\"=?, \"ID_Zona\"=?, \"Codigo_Ciudad_Zona\"=? WHERE \"Nombre\"=?");
    }

    public static PreparedStatement ActualizarCiudad(Connection con) {
        return getStatement(con, "UPDATE public.\"Ciudad\" SET \"Nombre\"=?, \"Pais\"=?, \"Codigo\"=? WHERE \"Nombre\"=?");
    }

    public static PreparedStatement ActualizarFarola(Connection con) {
        return getStatement(con, "UPDATE public.\"Farola\" SET \"Nombre_Calle\"=?, \"Id\"=?, \"ID_Zona_Calle\"=?, \"Codigo_Ciudad_Zona_Calle\"=?, \"Altura\"=? WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement ActualizarHoraPunta(Connection con) {
        return getStatement(con, "UPDATE public.\"Hora_Punta\" SET \"HoraInicio\"=?, \"HoraFin\"=?, \"Nombre_Calle\"=?, \"ID_Zona_Calle\"=?, \"Codigo_Ciudad_Zona_Calle\"=? WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement ActualizarMedicion(Connection con) {
        return getStatement(con, "UPDATE public.\"Medicion\" SET \"Valor\"=?, \"MarcaTemporal\"=?, \"Id_sensor\"=?, \"Tipo_Sensor\"=? WHERE \"Id_sensor\"=?");
    }

    public static PreparedStatement ActualizarPasoPeatones(Connection con) {
        return getStatement(con, "UPDATE public.\"Paso_Peatones\" SET \"Nombre_Calle\"=?, \"Id\"=?, \"ID_Zona_Calle\"=?, \"Codigo_Ciudad_Zona_Calle\"=?, \"Longitud\"=? WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement ActualizarSensorInfrarrojo(Connection con) {
        return getStatement(con, "UPDATE public.\"Sensor_Infrarrojo\" SET \"Id\"=?, \"Unidad_Medida\"=?, \"Id_Paso_Peatones\"=?, \"Nombre_Calle_Paso_Peatones\"=?, \"ID_Zona_Calle_Paso_Peatones\"=?, \"Codigo_Ciudad_Zona_Calle_Paso_Peatones\"=? WHERE \"Nombre_Calle_Paso_Peatones\"=?");
    }

    public static PreparedStatement ActualizarSensorLluvia(Connection con) {
        return getStatement(con, "UPDATE public.\"Sensor_Lluvia\" SET \"Id\"=?, \"UnidadMedida\"=?, \"Nombre_Calle\"=?, \"ID_Zona_Calle\"=?, \"Codigo_Ciudad_Zona_Calle\"=? WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement ActualizarSensorLuz(Connection con) {
        return getStatement(con, "UPDATE public.\"Sensor_Luz\" SET \"Id\"=?, \"UnidadMedida\"=?, \"Id_Farola\"=?, \"Nombre_Calle_Farola\"=?, \"ID_Zona_Calle_Farola\"=?, \"Codigo_Ciudad_Zona_Calle_Farola\"=? WHERE \"Nombre_Calle_Farola\"=?");
    }

    public static PreparedStatement ActualizarSensorMovimiento(Connection con) {
        return getStatement(con, "UPDATE public.\"Sensor_Movimiento\" SET \"Id\"=?, \"UnidadMedida\"=?, \"Nombre_Calle\"=?, \"ID_Zona_Calle\"=?, \"Codigo_Ciudad_Zona_Calle\"=? WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement ActualizarSensorTemperatura(Connection con) {
        return getStatement(con, "UPDATE public.\"Sensor_Temperatura\" SET \"Id\"=?, \"UnidadMedida\"=?, \"Nombre_Calle\"=?, \"ID_Zona_Calle\"=?, \"Codigo_Ciudad_Zona_Calle\"=? WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement ActualizarZona(Connection con) {
        return getStatement(con, "UPDATE public.\"Zona\" SET \"ID\"=?, \"Nombre\"=?, \"Codigo_Ciudad\"=? WHERE \"Nombre\"=?");
    }

    //Borrar datos en todas las tablas
    public static PreparedStatement BorrarCalle(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Calle\" WHERE \"Nombre\"=? AND \"ID_Zona\"=? AND \"Codigo_Ciudad_Zona\"=?");
    }

    public static PreparedStatement BorrarCiudad(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Ciudad\" WHERE \"Codigo\"=?");
    }

    public static PreparedStatement BorrarFarola(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Farola\" WHERE \"Nombre_Calle\"=? AND \"Id\"=? AND \"ID_Zona_Calle\"=? AND \"Codigo_Ciudad_Zona_Calle\"=?");
    }

    public static PreparedStatement BorrarHoraPunta(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Hora_Punta\" WHERE \"Nombre_Calle\"=? AND \"ID_Zona_Calle\"=? AND \"Codigo_Ciudad_Zona_Calle\"=? AND \"HoraInicio\"=? AND \"HoraFin\"=?");
    }

    public static PreparedStatement BorrarMedicion(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Medicion\" WHERE \"Id_sensor\"=?");
    }

    public static PreparedStatement BorrarPasoPeatones(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Paso_Peatones\" WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement BorrarSensorInfrarrojo(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Sensor_Infrarrojo\" WHERE \"Nombre_Calle_Paso_Peatones\"=?");
    }

    public static PreparedStatement BorrarSensorLluvia(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Sensor_Lluvia\" WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement BorrarSensorLuz(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Sensor_Luz\" WHERE \"Nombre_Calle_Farola\"=?");
    }

    public static PreparedStatement BorrarSensorMovimiento(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Sensor_Movimiento\" WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement BorrarSensorTemperatura(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Sensor_Temperatura\" WHERE \"Nombre_Calle\"=?");
    }

    public static PreparedStatement BorrarZona(Connection con) {
        return getStatement(con, "DELETE FROM public.\"Zona\" WHERE \"Nombre\"=?");
    }
}

