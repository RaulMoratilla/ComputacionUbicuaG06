package mqtt;

public class MQTTBroker 
{		
	private static int qos = 2;
	//Aqui tenemos que poner la IP de nuestro broker
	private static String broker = "tcp://172.20.10.10:1883";
	private static String clientId = "smartStreet";
		
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


