package basedatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            pstatament = con.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pstatament;
    }

    //**********LLAMADAS A BASE DE DATOS**********//

    //Select * from tablas
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
        return getStatement(con, "SELECT * FROM public.\"Calle\" WHERE \"ID_Zona\"=?");
    }

    public static PreparedStatement GetCallesDeCiudad(Connection con) {
        return getStatement(con, "SELECT * FROM public.\"Calle\" WHERE \"Codigo_Ciudad_Zona\"=?");
    }

    public static PreparedStatement GetCalles(Connection con) {
        return getStatement(con, "SELECT \"Nombre\" FROM public.\"Calle\"");
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

    public static PreparedStatement InsertarCiudad(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Ciudad\"(\"Nombre\", \"Pais\", \"Codigo\") VALUES (?, ?, ?)");
    }

    public static PreparedStatement InsertarZona(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Zona\"(\"ID\", \"Nombre\", \"Codigo_Ciudad\") VALUES (?, ?)");
    }

    public static PreparedStatement InsertarCalle(Connection con) {
        return getStatement(con, "INSERT INTO public.\"Calle\"(\"Nombre\", \"ID_Zona\", \"Codigo_Ciudad_Zona\") VALUES (?, ?, ?)");
    }


    /*public static PreparedStatement GetSensoresCalle(Connection con,  int idZona, String nombreCalle, String sensor) {
        String sensorTabla = "", relacionTabla = "";
        switch (sensor) {
            case "Lluvia":
                sensorTabla = "Sensor_Lluvia";
                relacionTabla = "Calle_SLluv";
                break;
            case "Infrarrojo":
                sensorTabla = "Sensor_Infrarrojo";
                relacionTabla = "Calle_SInfra";
                break;
            case "Movimiento":
                sensorTabla = "Sensor_Movimiento";
                relacionTabla = "Calle_SMov";
                break;
            case "Temperatura":
                sensorTabla = "Sensor_Temperatura";
                relacionTabla = "Calle_STemp";
                break;
            case "Luz":
                sensorTabla = "Sensor_Luz";
                relacionTabla = "Calle_SLuz";
                break;
            default:
                break;
        }
        return getStatement(con, "SELECT \"Id\""
                               + "FROM public.\"" + sensorTabla + "\" as S natural inner join public.\"" + relacionTabla + "\" as CS"
                               + "WHERE CS.\"Nombre_Calle\" in (SELECT \"Nombre\")"
                                                               + "FROM public.\"Calle\""
                                                               + "WHERE \"ID_Zona\" = " + idZona);
    }*/

}

