package logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import basedatos.*;

public class Logica {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-aa HH:MM:ss");

    public static Ciudad getCiudadBD(int codigoCiudad)
	{
        Ciudad ciudad = null;

		ConexionBD conector = new ConexionBD();
		Connection con = null;
		try
		{
			con = conector.crearConexion(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConexionBD.GetCiudades(con);
			Log.log.info("Query=> {}", ps.toString());
            ps.setInt(1, codigoCiudad);
			ResultSet rs = ps.executeQuery();
			ciudad = new Ciudad(rs.getInt("Codigo"), rs.getString("Nombre"), rs.getString("Pais"));
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
		} finally
		{
			conector.cerrarConexion(con);
		}
		return ciudad;
	}

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
				Ciudad ciudad = new Ciudad(rs.getInt("Codigo"), rs.getString("Nombre"), rs.getString("Pais"));
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
            zona = new Zona(rs.getInt("Codigo_Ciudad"), rs.getInt("ID"), rs.getString("Nombre"));

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

    public static ArrayList<Zona> getZonas() {
        ArrayList<Zona> zonas = new ArrayList<>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetZona(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            zonas.add(new Zona(rs.getInt("Codigo_Ciudad"), rs.getInt("ID"), rs.getString("Nombre")));

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.cerrarConexion(con);
        }
        return zonas;
    }

    public static ArrayList<Zona> getZonasCiudadBD(int codigoCiudad) {
        ArrayList<Zona> zonas = new ArrayList<Zona>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetZonasCiudad(con);
            ps.setInt(1, codigoCiudad);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Zona zona = new Zona(rs.getInt("Codigo_Ciudad"), rs.getInt("ID"), rs.getString("Nombre"));
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
    public static ArrayList<Sensor> getSensoresCalleTipoBD(int codigoCiudad, int idZona, String nombreCalle, String tipo) {
        ArrayList<Sensor> sensores = new ArrayList<Sensor>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetSensoresCalleTipo(con);
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

    public static ArrayList<Registro> getRegistrosUltimaSemana(Calle calle) {
        ArrayList<Registro> registros = new ArrayList<Registro>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetRegistrosUltimaSemana(con);
            ps.setInt(1, calle.getCodigoCiudad());
            ps.setInt(2, calle.getCodigoZona());
            ps.setString(3, calle.getNombre());
            ps.setTimestamp(4, Timestamp.valueOf(sdf.format(LocalDateTime.now())));
            ps.setTimestamp(5, Timestamp.valueOf(sdf.format(LocalDateTime.now().minusDays(7))));
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
                HoraPunta registro = new HoraPunta(rs.getInt("Codigo_Ciudad_Zona"), rs.getInt("ID_Zona_Calle"), rs.getString("Nombre_Calle"), rs.getTime("HoraInicio"), rs.getTime("HoraFin"), rs.getBoolean("Fijo"));
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

    //insertarSensorBD
    public static void insertarMedicionBD(Topicos newTopic) {
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            String unidadMedida = null;
            switch (newTopic.get_idSensores()) {
                case "sHum":
                    unidadMedida = "g/m3";
                    break;
                case "sTemp":
                    unidadMedida = "ºC";
                    break;
                case "sLuz":
                    unidadMedida = "cd";
                    break;
                case "movimiento":
                    unidadMedida = "";
                    break;
                case "sLluvia":
                    unidadMedida = "l/m2";
                    break;
                case "pasoPeatones":
                    unidadMedida = "";
                    break;
                default:
                    unidadMedida = "";
                    break;
            }

            PreparedStatement ps = ConexionBD.InsertarRegistro(con);
            ps.setInt(1, Integer.parseInt(newTopic.get_idCiudad()));
            ps.setInt(2, Integer.parseInt(newTopic.get_idZona()));
            ps.setString(3, newTopic.get_idCalle());
            ps.setString(4, newTopic.get_idSensores());
            Timestamp marcaTemporal = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(5, marcaTemporal);
            ps.setString(6, unidadMedida);
            ps.setFloat(7, Float.parseFloat(newTopic.getValue()));
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.cerrarConexion(con);
        }
    }
    
    //actualizarMedicionBD
    public static void actualizarMedicionBD(Topicos newTopic) {
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            String unidadMedida = null;
            switch (newTopic.get_idSensores()) {
                case "sHum":
                    unidadMedida = "g/m3";
                    break;
                case "sTemp":
                    unidadMedida = "ºC";
                    break;
                case "sLuz":
                    unidadMedida = "cd";
                    break;
                case "movimiento":
                    unidadMedida = "";
                    break;
                case "sLluvia":
                    unidadMedida = "l/m2";
                    break;
                case "pasoPeatones":
                    unidadMedida = "";
                    break;
                default:
                    unidadMedida = "";
                    break;
            }

            PreparedStatement ps = ConexionBD.ActualizarSensor(con);
            Timestamp marcaTemporal = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(1, marcaTemporal);
            ps.setString(2, unidadMedida);
            ps.setFloat(3, Float.parseFloat(newTopic.getValue()));
            
            ps.setInt(4, Integer.parseInt(newTopic.get_idCiudad()));
            ps.setInt(5, Integer.parseInt(newTopic.get_idZona()));
            ps.setString(6, newTopic.get_idCalle());
            ps.setString(7, newTopic.get_idSensores());
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.cerrarConexion(con);
        }
    }

    //obtenerNumeroMediciones
    public static int obtenerNumeroMediciones() {
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        int numeroMediciones = 0;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetNumMediciones(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            numeroMediciones = rs.getInt("NumMediciones");
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.cerrarConexion(con);
        }
        return numeroMediciones;
    }

    public static int obtenerNumeroMedicionesTipo(String tipo) {
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        int numeroMediciones = 0;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetNumMedicionesTipo(con);
            ps.setString(1, tipo);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            numeroMediciones = rs.getInt("NumMediciones");
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.cerrarConexion(con);
        }
        return numeroMediciones;
    }

    //insertarRegistroBD
    public static void insertarRegistroBD(Topicos newTopic) {
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            String unidadMedida = null;
            switch (newTopic.get_idSensores()) {
                case "sHum":
                    unidadMedida = "g/m3";
                    break;
                case "sTemp":
                    unidadMedida = "ºC";
                    break;
                case "sLuz":
                    unidadMedida = "cd";
                    break;
                case "movimiento":
                    unidadMedida = "";
                    break;
                case "sLluvia":
                    unidadMedida = "l/m2";
                    break;
                case "pasoPeatones":
                    unidadMedida = "";
                    break;
                default:
                    unidadMedida = "";
                    break;
            }

            PreparedStatement ps = ConexionBD.InsertarRegistro(con);
            ps.setInt(1, Integer.parseInt(newTopic.get_idCiudad()));
            ps.setInt(2, Integer.parseInt(newTopic.get_idZona()));
            ps.setString(3, newTopic.get_idCalle());
            ps.setString(4, newTopic.get_idSensores());
            Timestamp marcaTemporal = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(5, marcaTemporal);
            ps.setString(6, unidadMedida);
            ps.setFloat(7, Float.parseFloat(newTopic.getValue()));
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.cerrarConexion(con);
        }
    }

    public static ArrayList<HoraPunta> getHorasPuntaFija(Calle calle) {
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        ArrayList<HoraPunta> horasPunta = new ArrayList<>();
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetHorasPuntaCalleFija(con);
            ps.setInt(1, calle.getCodigoCiudad());
            ps.setInt(1, calle.getCodigoZona());
            ps.setString(2, calle.getNombre());
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoraPunta horaPunta = new HoraPunta(rs.getInt("Codigo_Ciudad_Zona_Calle"), 
                                                    rs.getInt("ID_Zona_Calle"), rs.getString("Nombre_Calle"), 
                                                    rs.getTime("horaInicio"), rs.getTime("horaFin"), rs.getBoolean("Fijo"));
                horasPunta.add(horaPunta);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.cerrarConexion(con);
        }
        return horasPunta;
    }

    //getHorasPuntaNoFija
    public static ArrayList<HoraPunta> getHorasPuntaNoFija(Calle calle) {
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        ArrayList<HoraPunta> horasPunta = new ArrayList<>();
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetHorasPuntaCalleNoFija(con);
            ps.setInt(1, calle.getCodigoCiudad());
            ps.setInt(1, calle.getCodigoZona());
            ps.setString(2, calle.getNombre());
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoraPunta horaPunta = new HoraPunta(rs.getInt("Codigo_Ciudad_Zona_Calle"), 
                                                    rs.getInt("ID_Zona_Calle"), rs.getString("Nombre_Calle"), 
                                                    rs.getTime("horaInicio"), rs.getTime("horaFin"), rs.getBoolean("Fijo"));
                horasPunta.add(horaPunta);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.cerrarConexion(con);
        }
        return horasPunta;
    }

    //eliminarHoraPuntaNoFija
    public static void eliminarHoraPuntaNoFija(Calle calle) {
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.EliminarHorasPuntaCalleNoFija(con);
            ps.setInt(1, calle.getCodigoCiudad());
            ps.setInt(2, calle.getCodigoZona());
            ps.setString(3, calle.getNombre());
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.cerrarConexion(con);
        }
    }

    public static void insertarHoraPuntaNoFija(int codigoCiudad, int codigoZona, String nombre, Time time, Time time2,
            boolean b) {
    }
}
