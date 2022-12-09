package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import basedatos.*;

public class Logica {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static ArrayList<Ciudad> getCiudadesBD()
	{
		ArrayList<Ciudad> ciudades = new ArrayList<Ciudad>();
		
		ConexionBD conector = new ConexionBD();
		Connection con = null;
		try
		{
			con = conector.crearConexion(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConexionBD.GetCiudades(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Ciudad ciudad = new Ciudad(rs.getString("Codigo"), rs.getString("Nombre"), rs.getString("Pais"));
				ciudades.add(ciudad);
			}
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			ciudades = new ArrayList<Ciudad>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			ciudades = new ArrayList<Ciudad>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			ciudades = new ArrayList<Ciudad>();
		} finally
		{
			conector.cerrarConexion(con);
		}
		return ciudades;
	}

    public static Zona getZonaBD(int codigoCiudad, int idZona) {
        Zona zona = null;

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetZona(con);
            ps.setInt(1, codigoCiudad);
            ps.setInt(2, idZona);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            zona = new Zona(rs.getInt("ID"), rs.getString("Nombre"), rs.getString("Codigo_Ciudad"));

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.cerrarConexion(con);
        }
        return zona;
    }

    public static ArrayList<Zona> getZonasCiudadBD(String codigoCiudad) {
        ArrayList<Zona> zonas = new ArrayList<Zona>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetZonasCiudad(con);
            ps.setString(1, codigoCiudad);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Zona zona = new Zona(rs.getInt("ID"), rs.getString("Nombre"), rs.getString("Codigo_Ciudad"));
                zonas.add(zona);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            zonas = new ArrayList<Zona>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            zonas = new ArrayList<Zona>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            zonas = new ArrayList<Zona>();
        } finally {
            conector.cerrarConexion(con);
        }
        return zonas;
    }

    public static Calle getCalleBD(int codigoCiudad, int idZona, String nombreCalle) {
        Calle calle = null;

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetCalle(con);
            ps.setInt(1, codigoCiudad);
            ps.setInt(2, idZona);
            ps.setString(3, nombreCalle);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            calle = new Calle(rs.getInt("Codigo_Ciudad_Zona"), rs.getInt("ID_Zona"), rs.getString("Nombre"));

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.cerrarConexion(con);
        }
        return calle;
    }

    public static ArrayList<Calle> getCallesZonaBD(int codigoCiudad, int idZona) {
        ArrayList<Calle> calles = new ArrayList<Calle>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetCallesZona(con);
            ps.setInt(1, codigoCiudad);
            ps.setInt(2, idZona);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Calle calle = new Calle(rs.getInt("Codigo_Ciudad_Zona"), rs.getInt("ID_Zona"), rs.getString("Nombre"));
                calles.add(calle);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            calles = new ArrayList<Calle>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            calles = new ArrayList<Calle>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            calles = new ArrayList<Calle>();
        } finally {
            conector.cerrarConexion(con);
        }
        return calles;
    }

    //getSensoresCalleDB
    public static ArrayList<Sensor> getSensoresCalleBD(int codigoCiudad, int idZona, String nombreCalle) {
        ArrayList<Sensor> sensores = new ArrayList<Sensor>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetSensoresCalle(con);
            ps.setInt(1, codigoCiudad);
            ps.setInt(2, idZona);
            ps.setString(3, nombreCalle);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sensor sensor = new Sensor(rs.getInt("Codigo_Ciudad_Zona"), rs.getInt("ID_Zona_Calle"), rs.getString("Nombre_Calle"), rs.getString("Tipo"), rs.getString("UnidadMedida"), rs.getTimestamp("MarcaTemporal"), rs.getFloat("Valor"));
                sensores.add(sensor);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            sensores = new ArrayList<Sensor>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            sensores = new ArrayList<Sensor>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            sensores = new ArrayList<Sensor>();
        } finally {
            conector.cerrarConexion(con);
        }
        return sensores;
    }

    //getSensoresTipoCalleBD
    public static ArrayList<Sensor> getSensoresTipoCalleBD(int codigoCiudad, int idZona, String nombreCalle, String tipo) {
        ArrayList<Sensor> sensores = new ArrayList<Sensor>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetSensoresTipoCalle(con);
            ps.setInt(1, codigoCiudad);
            ps.setInt(2, idZona);
            ps.setString(3, nombreCalle);
            ps.setString(4, tipo);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sensor sensor = new Sensor(rs.getInt("Codigo_Ciudad_Zona"), rs.getInt("ID_Zona_Calle"), rs.getString("Nombre_Calle"), rs.getString("Tipo"), rs.getString("UnidadMedida"), rs.getTimestamp("MarcaTemporal"), rs.getFloat("Valor"));
                sensores.add(sensor);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            sensores = new ArrayList<Sensor>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            sensores = new ArrayList<Sensor>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            sensores = new ArrayList<Sensor>();
        } finally {
            conector.cerrarConexion(con);
        }
        return sensores;
    }

    public static ArrayList<Registro> getRegistrosCalleBD(int codigoCiudad, int idZona, String nombreCalle) {
        ArrayList<Registro> registros = new ArrayList<Registro>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetRegistrosCalle(con);
            ps.setInt(1, codigoCiudad);
            ps.setInt(2, idZona);
            ps.setString(3, nombreCalle);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Registro registro = new Registro(rs.getInt("Codigo_Ciudad_Zona"), rs.getInt("ID_Zona_Calle"), rs.getString("Nombre_Calle"), rs.getString("Tipo"), rs.getString("UnidadMedida"), rs.getTimestamp("MarcaTemporal"), rs.getFloat("Valor"));
                registros.add(registro);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            registros = new ArrayList<Registro>();
        } finally {
            conector.cerrarConexion(con);
        }
        return registros;
    }    

    //getRegistrosCalleTipoBD
    public static ArrayList<Registro> getRegistrosCalleTipoBD(int codigoCiudad, int idZona, String nombreCalle, String tipo) {
        ArrayList<Registro> registros = new ArrayList<Registro>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetRegistrosCalleTipo(con);
            ps.setInt(1, codigoCiudad);
            ps.setInt(2, idZona);
            ps.setString(3, nombreCalle);
            ps.setString(4, tipo);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Registro registro = new Registro(rs.getInt("Codigo_Ciudad_Zona"), rs.getInt("ID_Zona_Calle"), rs.getString("Nombre_Calle"), rs.getString("Tipo"), rs.getString("UnidadMedida"), rs.getTimestamp("MarcaTemporal"), rs.getFloat("Valor"));
                registros.add(registro);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            registros = new ArrayList<Registro>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            registros = new ArrayList<Registro>();
        } finally {
            conector.cerrarConexion(con);
        }
        return registros;
    }

    //getHorasPuntaCalleBD
    public static ArrayList<HoraPunta> getHorasPuntaCalleBD(int codigoCiudad, int idZona, String nombreCalle) {
        ArrayList<HoraPunta> horasPunta = new ArrayList<HoraPunta>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetHorasPuntaCalle(con);
            ps.setInt(1, codigoCiudad);
            ps.setInt(2, idZona);
            ps.setString(3, nombreCalle);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoraPunta registro = new HoraPunta(rs.getInt("Codigo_Ciudad_Zona"), rs.getInt("ID_Zona_Calle"), rs.getString("Nombre_Calle"), rs.getTime("HoraInicio"), rs.getTime("HoraFin"));
                horasPunta.add(registro);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            horasPunta = new ArrayList<HoraPunta>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            horasPunta = new ArrayList<HoraPunta>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            horasPunta = new ArrayList<HoraPunta>();
        } finally {
            conector.cerrarConexion(con);
        }
        return horasPunta;
    }
}
