package mqtt;

public class MQTTBroker 
{		
	private static int qos = 2;
	//Aqui tenemos que poner la IP de nuestro broker
	private static String broker = "tcp://127.0.0.1:1883";
	private static String clientId = "SmartStreet";
		
	public MQTTBroker()
	{
	}

	public static int getQos() {
		return qos;
	}

	public static String getBroker() {
		return broker;
	}

	public static String getClientId() {
		return clientId;
	}			
}


