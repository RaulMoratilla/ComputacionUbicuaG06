//WiFiClient espClient;
//PubSubClient mqttClient(espClient);

/*void SuscribeMqtt(Luz luces[]) {
  for (Luz l : luces) {
    mqttClient.subscribe(l.topic);
  }
}

String payload;
void PublisMqtt(int tipo, int data, int tiempo, String topic) {
    payload = "";
    payload = String(tipo);
    payload.concat("-");
    payload.concat((String)data);
    payload.concat("-");
    payload.concat((String)tiempo);
    mqttClient.publish(topic, (char*)payload.c_str());
}

String content = "";
void OnMqttReceived(char* topic, byte* payload, unsigned int length) {
    
    content = "";
    for (size_t i = 0; i < length; i++) {
        content.concat((char)payload[i]);
    }
    
    int tipo, dato, tiempo;
    tipo = content.substring(0, content.indexOf("-")).toInt();
    String aux = content.substring(content.indexOf("-")+1, -1);
    dato = aux.substring(0, content.indexOf("-")).toInt();
    tiempo = aux.substring(content.indexOf("-")+1, -1).toInt();

    switch (tipo) {
      case 0:
        temperatura = dato;
        gestionLuzTemperaturaHumedad(luzTH);
        break;
      case 1:
        humedad = dato;
        gestionLuzTemperaturaHumedad(luzTH);
        break;
      case 2:
        nivelLuz = dato;
        gestionLuzNoche(luzNoche);
        break;
      case 3:
        if (dato == -1 && noche) {
          gestionarLuz(255, luzNoche);
          msRevNoche = tiempo;
        } else if (msRevNoche + 5000 > millis()) {
          gestionLuzNoche(luzNoche);
        }
        break;
    }
}*/
