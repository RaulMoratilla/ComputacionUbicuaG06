package logica;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import mqtt.MQTTBroker;
import mqtt.MQTTSuscriber;

@WebListener
public class InicializacionProyecto implements ServletContextListener {
    
    @Override
	public void contextDestroyed(ServletContextEvent sce)
	{
	}
	
	@Override
	/**
	 *	ES: Metodo empleado para detectar la inicializacion del servidor	<br>
	 * 	EN: Method used to detect server initialization
	 * 	@param sce <br>
	 * 	ES: Evento de contexto creado durante el arranque del servidor	<br>
	 * 	EN: Context event created during server launch
	 */
	public void contextInitialized(ServletContextEvent sce)
	{
		try {
            String [] cmd = {"systemctl", "start", "mosquitto.service"};
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }

		Log.log.info("-->Suscribe Topics<--");
		MQTTBroker broker = new MQTTBroker();
		MQTTSuscriber suscriber = new MQTTSuscriber();
		suscriber.searchTopicsToSuscribe(broker);

        HiloCreadorHilosAlgoritmo hiloPadre = new HiloCreadorHilosAlgoritmo();
        hiloPadre.start();;
	}	

}
