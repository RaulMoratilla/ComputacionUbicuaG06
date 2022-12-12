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

import basedatos.Calle;
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
				topics.add(rsCity.getString("maqueta")+"/#");
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
		
		if(topic.contains("pasoCebra"))
		{
			String tipo = topics[5];

			newTopic.set_idCiudad(topics[0].replace("maqueta", ""));
			newTopic.set_idZona(topics[1].replace("zonaMaqueta", ""));
			newTopic.set_idCalle(topics[3].replace("calle", ""));
			newTopic.set_idSensores(topics[5]);
			Log.logmqtt.info("Mensaje from ciudad{}, zona{}, calles, calle{}, pasoCebra, {}: {}", 
			           
					newTopic.get_idCiudad(), newTopic.get_idZona(), newTopic.get_idCalle(), newTopic.get_idSensores(), message.toString());
			
			if (Logica.obtenerNumeroMedicionesTipo(tipo)==0){
				Logica.insertarMedicionBD(newTopic);
				Logica.insertarRegistroBD(newTopic);
			}
			else if (Logica.obtenerNumeroMedicionesTipo(tipo)==1){
				Logica.actualizarMedicionBD(newTopic);
				Logica.insertarRegistroBD(newTopic);
			}
		}
		else if (topic.contains("movimiento"))
		{
			String tipo = topics[4];
			newTopic.set_idCiudad(topics[0].replace("maqueta", ""));
			newTopic.set_idZona(topics[1].replace("zonaMaqueta", ""));
			newTopic.set_idCalle(topics[3]);
			newTopic.set_idPasoP(tipo);
			Log.logmqtt.info("Mensaje from ciudad{}, zona{}, calles, {}, {}: {}", 
			           
					newTopic.get_idCiudad(), newTopic.get_idZona(), newTopic.get_idCalle(), newTopic.get_idSensores(), message.toString());
			
			if (Logica.obtenerNumeroMedicionesTipo(tipo)==0){
				Logica.insertarMedicionBD(newTopic);
				Logica.insertarRegistroBD(newTopic);
			}
			else if (Logica.obtenerNumeroMedicionesTipo(tipo)==1){
				Logica.actualizarMedicionBD(newTopic);
				Logica.insertarRegistroBD(newTopic);
			}
		}
		else if(topic.contains("humedad") || topic.contains("luz") || topic.contains("temperatura") || topic.contains("lluvia"))
		{
			String tipo = topics[3];

			newTopic.set_idCiudad(topics[0].replace("ciudad", ""));
			newTopic.set_idZona(topics[1].replace("zona", ""));
			newTopic.set_idSensores(tipo);
			Log.logmqtt.info("Mensaje from ciudad{}, zona{}, datos, {}: {}", 
					newTopic.get_idCiudad(), newTopic.get_idZona(), newTopic.get_idSensores(), message.toString());

			ArrayList<Calle> calles = Logica.getCallesZonaBD(Integer.parseInt(newTopic.get_idCiudad()), Integer.parseInt(newTopic.get_idZona()));

			for (Calle c: calles) {
				newTopic.set_idCalle(c.getNombre());
				if (Logica.obtenerNumeroMedicionesTipo(tipo)==0){
					Logica.insertarMedicionBD(newTopic);
					Logica.insertarRegistroBD(newTopic);
				}
				else if (Logica.obtenerNumeroMedicionesTipo(tipo)==1){
					Logica.actualizarMedicionBD(newTopic);
					Logica.insertarRegistroBD(newTopic);
				}
			}
		}
		else if(topic.contains("calle"))
    	{
			newTopic.set_idCiudad(topics[0].replace("maqueta", ""));
			newTopic.set_idZona(topics[1].replace("zonaMaqueta", ""));
			newTopic.set_idCalle(topics[2].replace("calle", ""));
			Log.logmqtt.info("Mensaje de ciudad{}, zona{}, calles, calle{}: {}", newTopic.get_idCiudad(), newTopic.get_idZona(), newTopic.get_idCalle(), message.toString());
		}
	   	else if(topic.contains("zonaMaqueta"))
    	{
			newTopic.set_idCiudad(topics[0].replace("ciudad", ""));
			newTopic.set_idZona(topics[1].replace("zona", ""));
			Log.logmqtt.info("Mensaje de ciudad{}, zona{}: {}",	newTopic.get_idCiudad(), newTopic.get_idZona(), message.toString());
		}
		else
		{
			if(topic.contains("maqueta"))
			{
				newTopic.set_idCiudad(topics[0].replace("ciudad", ""));
				Log.logmqtt.info("Mensaje de ciudad{}: {}", 
						newTopic.get_idCiudad(), message.toString());
			}
		}
    }

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) 
	{		
	}
}
