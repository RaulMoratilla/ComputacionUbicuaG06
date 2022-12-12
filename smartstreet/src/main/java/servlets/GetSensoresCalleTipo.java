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

import basedatos.Sensor;
import logica.Log;
import logica.Logica;

@WebServlet("/GetSensoresCalleTipo")
public class GetSensoresCalleTipo extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public GetSensoresCalleTipo() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Obtener informacion de la medicion tomada en una calle por un tipo de sensor --");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try 
		{
            int codigoCiudad = Integer.parseInt(request.getParameter("codigoCiudad"));
            int idZona = Integer.parseInt(request.getParameter("idZona"));
            String nombreCalle = String.valueOf(request.getParameter("nombreCalle"));
            String tipoSensor = String.valueOf(request.getParameter("tipoSensor"));

			ArrayList<Sensor> sensoresCT =Logica.getSensoresCalleTipoBD(codigoCiudad, idZona, nombreCalle, tipoSensor);
			String jsonSensoresCT = new Gson().toJson(sensoresCT);
			Log.log.info("JSON Values=> {}", jsonSensoresCT);
			out.println(jsonSensoresCT);
            
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
