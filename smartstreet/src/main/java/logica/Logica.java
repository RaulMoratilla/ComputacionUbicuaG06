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
}
