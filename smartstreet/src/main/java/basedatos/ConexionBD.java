package basedatos;

import java.sql.*;

public class ConexionBD {

    Connection conexion;

    public ConexionBD() {
        
        try { 
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5433/new_database", "postgres", "postgres");
            if (conexion != null) {
                System.out.println("Conexión a base de datos ... Ok");
            }

        } catch (java.sql.SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    }

    public Connection getConexion ()
    {
        return conexion;
    }

    public void cierraConexion() {
        try {
            conexion.close();
        } 
        catch (Exception exception) {
            System.out.println("Error cerrando la conexion");
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
    public static void insertar(String query){
        Connection con = null;
        PreparedStatement pstatament = null;
        try {
            con = new ConexionBD().getConexion();
            pstatament = getStatement(con, query);
            pstatament.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            try {
                if(pstatament != null){
                    pstatament.close();
                }
                if(con != null){
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static PreparedStatement GetCiudades(Connection con) {
        return getStatement(con, "SELECT \"Nombre\" FROM public.\"Ciudad\"");
    }

    public static PreparedStatement GetZonas(Connection con, int codigoCiudad) {
        return getStatement(con, "SELECT \"ID_Zona\" FROM public.\"Zona\""
                               + "WHERE \"Codigo_Ciudad\" = " + codigoCiudad);
    }

    public static PreparedStatement GetCalles(Connection con, int idZona) {
        return getStatement(con, "SELECT \"Nombre\" FROM public.\"Calle\""
                               + "WHERE \"ID_Zona\" = " + idZona);
    }

    public static PreparedStatement GetSensoresCalle(Connection con, String nombreCalle, int idZona, String sensor) {
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
    }

}

