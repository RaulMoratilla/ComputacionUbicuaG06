
package mqtt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import basedatos.ConexionBD;
import basedatos.Topicos;
import logica.Log;
import logica.Logica;

public class MQTTSuscriber implements MqttCallback
{
	public void searchTopicsToSuscribe(MQTTBroker broker){
		ConexionBD conector = new ConexionBD();
		Connection con = null;
		ArrayList<String> topics = new ArrayList<>();
		try{
			con = conector.crearConexion(true);
			Log.logmqtt.debug("Database Connected");
			
			PreparedStatement psCity = ConexionBD.GetCiudades(con);
			Log.logmqtt.debug("Query to search cities=> {}", psCity.toString());
			ResultSet rsCity = psCity.executeQuery();
			while (rsCity.next()){
				topics.add(rsCity.getString("Nombre")+"/#");
			}
			suscribeTopic(broker, topics);			
		} 
		catch (NullPointerException e){Log.logmqtt.error("Error: {}", e);} 
		catch (Exception e){Log.logmqtt.error("Error:{}", e);} 
		finally{conector.cerrarConexion(con);}
	}
	
	public void suscribeTopic(MQTTBroker broker, ArrayList<String> topics)
	{
		Log.logmqtt.debug("Suscribe to topics");
        MemoryPersistence persistence = new MemoryPersistence();
        try
        {
            MqttClient sampleClient = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            Log.logmqtt.debug("Mqtt Connecting to broker: " + MQTTBroker.getBroker());
            sampleClient.connect(connOpts);
            Log.logmqtt.debug("Mqtt Connected");
            sampleClient.setCallback(this);
            for (int i = 0; i <topics.size(); i++) 
            {
                sampleClient.subscribe(topics.get(i));
                Log.logmqtt.info("Subscribed to {}", topics.get(i));
			}
            
        } catch (MqttException me) {
            Log.logmqtt.error("Error suscribing topic: {}", me);
        } catch (Exception e) {
            Log.logmqtt.error("Error suscribing topic: {}", e);
        }
	}
	
	@Override
	public void connectionLost(Throwable cause) 
	{	
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception 
	{
		Log.logmqtt.info("{}: {}", topic, message.toString());
		String[] topics = topic.split("/");
		Topicos newTopic = new Topicos();
		newTopic.setValue(message.toString());
		
		if(topic.contains("pasoCebra1"))
		{
			newTopic.set_idCiudad(topics[0].replace("ciudad1", ""));
			newTopic.set_idZona(topics[1].replace("zona1", ""));
			newTopic.set_idCalle(topics[2].replace("calle1", ""));
			newTopic.set_idPasoP(topics[5].replace("sensores", ""));
			Log.logmqtt.info("Mensaje from ciudad{}, zona{}, calle{}, sensor{}: {}", 
			           
					newTopic.get_idCiudad(), newTopic.get_idZona(), newTopic.get_idCalle(), newTopic.get_idSensores(), message.toString());
			

			/*
			 * Cambiar a buscar si hay 1 medicion de un tipo concreto
			 */
			if (Logica.obtenerNumeroMediciones()==0){
				Logica.insertarMedicionBD(newTopic);
				Logica.insertarRegistroBD(newTopic);
			}
			else if (Logica.obtenerNumeroMediciones()==1){
				Logica.actualizarMedicionBD(newTopic);
				Logica.insertarRegistroBD(newTopic);
			}
		}
		else if (topic.contains("horarioConflictivo")) {
			newTopic.set_idCiudad(topics[0].replace("ciudad1", ""));
			newTopic.set_idZona(topics[1].replace("zona1", ""));
			newTopic.set_idCalle(topics[2].replace("calle1", ""));
			newTopic.set_idHoraConf(topics[4].replace("sensores", ""));
			Log.logmqtt.info("Mensaje from ciudad{}, zona{}, calle{}, horarioConflictivo{}: {}", 
					newTopic.get_idCiudad(), newTopic.get_idZona(), newTopic.get_idCalle(), newTopic.get_idSensores(), message.toString());
		}
		else if(topic.contains("humedad") || topic.contains("movimiento") || topic.contains("luz") || topic.contains("temperatura") || topic.contains("lluvia"))
		{
			newTopic.set_idCiudad(topics[0].replace("ciudad1", ""));
			newTopic.set_idZona(topics[1].replace("zona1", ""));
			newTopic.set_idCalle(topics[2].replace("calle1", ""));
			newTopic.set_idSensores(topics[4].replace("sensores", ""));
			Log.logmqtt.info("Mensaje from ciudad{}, zona{}, calle{}, sensor{}: {}", 
					newTopic.get_idCiudad(), newTopic.get_idZona(), newTopic.get_idCalle(), newTopic.get_idSensores(), message.toString());
		
			/*
			 * Cambiar a buscar si hay 1 medicion de un tipo concreto
			 */
			if (Logica.obtenerNumeroMediciones()==0){
				Logica.insertarMedicionBD(newTopic);
				Logica.insertarRegistroBD(newTopic);
			}
			else if (Logica.obtenerNumeroMediciones()==1){
				Logica.actualizarMedicionBD(newTopic);
				Logica.insertarRegistroBD(newTopic);
			}
		}
		else if(topic.contains("calle1"))
    	{
			newTopic.set_idCiudad(topics[0].replace("ciudad1", ""));
			newTopic.set_idZona(topics[1].replace("zona1", ""));
			newTopic.set_idCalle(topics[2].replace("Calle", ""));
			Log.logmqtt.info("Mensaje de ciudad{}, zona{}, calle{}: {}", newTopic.get_idCiudad(), newTopic.get_idZona(), newTopic.get_idCalle(), message.toString());
		}
	   	else if(topic.contains("zonaMaqueta"))
    	{
			newTopic.set_idCiudad(topics[0].replace("ciudad1", ""));
			newTopic.set_idZona(topics[1].replace("zona1", ""));
			Log.logmqtt.info("Mensaje de ciudad{}, zona{}: {}",	newTopic.get_idCiudad(), newTopic.get_idZona(), message.toString());
		}
		else
		{
			if(topic.contains("maqueta"))
			{
				newTopic.set_idCiudad(topics[0].replace("ciudad1", ""));
				Log.logmqtt.info("Mensaje from city{}: {}", 
						newTopic.get_idCiudad(), message.toString());
			}
		}
    }

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) 
	{		
	}
}
