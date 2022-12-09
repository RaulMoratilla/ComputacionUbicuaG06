package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import basedatos.Zona;
import logica.Log;
import logica.Logica;

@WebServlet("/GetZonasCiudad")
public class GetZonasCiudad extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public GetZonasCiudad() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Obtener informacion de Zonas de una Ciudad de la BD --");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try 
		{
            int codigoCiudad = Integer.parseInt(request.getParameter("codigoCiudad"));

			ArrayList<Zona> zonasC =Logica.getZonasCiudadBD(codigoCiudad);
			String jsonZonas = new Gson().toJson(zonasC);
			Log.log.info("JSON Values=> {}", jsonZonas);
			out.println(jsonZonas);
            
		} catch (NumberFormatException nfe) 
		{
			out.println("-1");
			Log.log.error("Number Format Exception: {}", nfe);
		} catch (IndexOutOfBoundsException iobe) 
		{
			out.println("-1");
			Log.log.error("Index out of bounds Exception: {}", iobe);
		} catch (Exception e) 
		{
			out.println("-1");
			Log.log.error("Exception: {}", e);
		} finally 
		{
			out.close();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
