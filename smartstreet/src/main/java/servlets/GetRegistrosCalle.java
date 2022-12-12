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

import basedatos.Registro;
import logica.Log;
import logica.Logica;

@WebServlet("/GetRegistrosCalle")
public class GetRegistrosCalle extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public GetRegistrosCalle() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Obtener informacion del registro almacenado de mediciones tomadas en una calle --");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try 
		{
            int codigoCiudad = Integer.parseInt(request.getParameter("codigoCiudad"));
            int idZona = Integer.parseInt(request.getParameter("idZona"));
            String nombreCalle = String.valueOf(request.getParameter("nombreCalle"));

			ArrayList<Registro> registrosC =Logica.getRegistrosCalleBD(codigoCiudad, idZona, nombreCalle);
			String jsonRegistrosC = new Gson().toJson(registrosC);
			Log.log.info("JSON Values=> {}", jsonRegistrosC);
			out.println(jsonRegistrosC);
            
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
