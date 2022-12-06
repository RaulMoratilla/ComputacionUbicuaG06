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

    public static ArrayList<Zona> getZonasBD() {
        ArrayList<Zona> zonas = new ArrayList<Zona>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetZonas(con);
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

    public static ArrayList<Zona> getZonasCiudadBD(String codigoCiudad) {
        ArrayList<Zona> zonas = new ArrayList<Zona>();

        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try {
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConexionBD.GetZonasDeCiudad(con);
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

    public static ArrayList<Calle> getCallesBD(){
        ArrayList<Calle> calles = new ArrayList<Calle>();
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try{
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetCalles(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Calle calle = new Calle(rs.getString("Nombre"), rs.getString("ID_Zona"), rs.getString("Codigo_Ciudad_Zona"));
                calles.add(calle);
            }
        }catch(SQLException e){
            Log.log.error("Error: {}", e);
            calles = new ArrayList<Calle>();
        }catch(NullPointerException e){
            Log.log.error("Error: {}", e);
            calles = new ArrayList<Calle>();
        }catch(Exception e){
            Log.log.error("Error:{}", e);
            calles = new ArrayList<Calle>();
        }finally{
            conector.cerrarConexion(con);
        }
        return calles;
    }

    public static ArrayList<Calle> getCallesCiudadBD(String idZona, String codigoCiudad){
        ArrayList<Calle> calles = new ArrayList<Calle>();
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try{
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetCallesDeCiudad(con);
            ps.setString(1, idZona);
            ps.setString(2, codigoCiudad);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Calle calle = new Calle(rs.getString("Nombre"), rs.getString("ID_Zona"), rs.getString("Codigo_Ciudad_Zona"));
                calles.add(calle);
            }
        }catch(SQLException e){
            Log.log.error("Error: {}", e);
            calles = new ArrayList<Calle>();
        }catch(NullPointerException e){
            Log.log.error("Error: {}", e);
            calles = new ArrayList<Calle>();
        }catch(Exception e){
            Log.log.error("Error:{}", e);
            calles = new ArrayList<Calle>();
        }finally{
            conector.cerrarConexion(con);
        }
        return calles;
    }

    public static Calle getCalleBD(String nombreCalle, String idZona, String codigoCiudad){
        Calle calle = null;
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try{
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetCalle(con);
            ps.setString(1, nombreCalle);
            ps.setString(2, idZona);
            ps.setString(3, codigoCiudad);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                calle = new Calle(rs.getString("Nombre"), rs.getString("ID_Zona"), rs.getString("Codigo_Ciudad_Zona"));
            }
        }catch(SQLException e){
            Log.log.error("Error: {}", e);
            calle = null;
        }catch(NullPointerException e){
            Log.log.error("Error: {}", e);
            calle = null;
        }catch(Exception e){
            Log.log.error("Error:{}", e);
            calle = null;
        }finally{
            conector.cerrarConexion(con);
        }
        return calle;
    }

    //getMedicionBD
    public static ArrayList<Medicion> getMedicionesBD(){
        ArrayList<Medicion> mediciones = new ArrayList<Medicion>();
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try{
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetMediciones(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Medicion medicion = new Medicion(rs.getInt("ID"), rs.getInt("Valor"), rs.getTimestamp("MarcaTemporal"), rs.getInt("Unidad"));
                mediciones.add(medicion);
            }
        }catch(SQLException e){
            Log.log.error("Error: {}", e);
            mediciones = new ArrayList<Medicion>();
        }catch(NullPointerException e){
            Log.log.error("Error: {}", e);
            mediciones = new ArrayList<Medicion>();
        }catch(Exception e){
            Log.log.error("Error:{}", e);
            mediciones = new ArrayList<Medicion>();
        }finally{
            conector.cerrarConexion(con);
        }
        return mediciones;
    }

    //getMedicionTipo
    public static ArrayList<Medicion> getMedicionesTipoBD(String tipoSensor){
        ArrayList<Medicion> mediciones = new ArrayList<Medicion>();
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try{
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetMedicionesTipo(con);
            ps.setString(1, tipoSensor);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Medicion medicion = new Medicion(rs.getInt("ID"), rs.getInt("Valor"), rs.getTimestamp("MarcaTemporal"), rs.getInt("Unidad"));
                mediciones.add(medicion);
            }
        }catch(SQLException e){
            Log.log.error("Error: {}", e);
            mediciones = new ArrayList<Medicion>();
        }catch(NullPointerException e){
            Log.log.error("Error: {}", e);
            mediciones = new ArrayList<Medicion>();
        }catch(Exception e){
            Log.log.error("Error:{}", e);
            mediciones = new ArrayList<Medicion>();
        }finally{
            conector.cerrarConexion(con);
        }
        return mediciones;
    }

    //getMedicionesSensor
    public static ArrayList<Medicion> getMedicionesSensorBD(String tipoSensor, String idSensor){
        ArrayList<Medicion> mediciones = new ArrayList<Medicion>();
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try{
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetMedicionesSensor(con);
            ps.setString(1, tipoSensor);
            ps.setString(2, idSensor);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Medicion medicion = new Medicion(rs.getInt("ID"), rs.getInt("Valor"), rs.getTimestamp("MarcaTemporal"), rs.getInt("Unidad"));
                mediciones.add(medicion);
            }
        }catch(SQLException e){
            Log.log.error("Error: {}", e);
            mediciones = new ArrayList<Medicion>();
        }catch(NullPointerException e){
            Log.log.error("Error: {}", e);
            mediciones = new ArrayList<Medicion>();
        }catch(Exception e){
            Log.log.error("Error:{}", e);
            mediciones = new ArrayList<Medicion>();
        }finally{
            conector.cerrarConexion(con);
        }
        return mediciones;
    }

    //getSensores
    public static ArrayList<Sensor> getSensoresBD(){
        ArrayList<Sensor> sensores = new ArrayList<Sensor>();
        ConexionBD conector = new ConexionBD();
        Connection con = null;
        try{
            con = conector.crearConexion(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConexionBD.GetSensores(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Sensor sensor = new Sensor(rs.getString("ID"), rs.getString("Tipo"), rs.getString("ID_Calle"), rs.getString("Codigo_Ciudad_Calle"));
                sensores.add(sensor);
            }
        }catch(SQLException e){
            Log.log.error("Error: {}", e);
            sensores = new ArrayList<Sensor>();
        }catch(NullPointerException e){
            Log.log.error("Error: {}", e);
            sensores = new ArrayList<Sensor>();
        }catch(Exception e){
            Log.log.error("Error:{}", e);
            sensores = new ArrayList<Sensor>();
        }finally{
            conector.cerrarConexion(con);
        }
        return sensores;
    }
}
