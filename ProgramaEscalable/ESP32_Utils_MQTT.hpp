/**
 * Se suscribe a los tópicos de las luces pasadas como parámetros
*/
void SuscribeLucesMqtt(Luz luces[], int tamArray) {
  for (int i = 0 ; i < tamArray ; i++) {
    mqttClient.subscribe(luces[i].topic);
  }
}

/**
 * Se suscribe a los tópicos de las luces de noche
*/
void SuscribeLucesNocheMqtt(LuzNoche luces[], int tamArray) {
  for (int i = 0 ; i < tamArray ; i++) {
    mqttClient.subscribe(luces[i].topicLuz);
    mqttClient.subscribe(luces[i].topicMovimiento);
  }
}

/**
 * Se suscribe a los tópicos de las luces
*/
void SuscribeMqtt() {
  SuscribeLucesMqtt(lucesTemp, N_LUZ_TEMP);
  SuscribeLucesMqtt(lucesHum, N_LUZ_HUM);
  SuscribeLucesMqtt(lucesLluvia, N_LUZ_LLUVIA);
  SuscribeLucesMqtt(lucesPasoCebra, N_LUZ_CEBRA);
  SuscribeLucesMqtt(lucesHorario, N_LUZ_HORARIO);
  SuscribeLucesNocheMqtt(lucesNoche, N_LUZ_NOCHE); 
}

/**
 * Se suscribe a un tópico
*/
void suscribirTopic(char* topic) {
  mqttClient.subscribe(topic);
}

/**
 * Se inicia MQTT
*/
void InitMqtt() {
  Serial.println(MQTT_BROKER_ADRESS);
  Serial.println(MQTT_PORT);
  mqttClient.setServer(MQTT_BROKER_ADRESS, MQTT_PORT);
  SuscribeMqtt();
  mqttClient.setCallback(OnMqttReceived);
}

/**
 * Se conecta a MQTT
*/
void ConnectMqtt() {
  while (!mqttClient.connected()) {
    if (mqttClient.connect(MQTT_CLIENT_NAME,"","")) {
      SuscribeMqtt();
    }
    else {
      Serial.print("Failed MQTT connection, rc=");
      Serial.print(mqttClient.state());
      Serial.println(" try again in 5 seconds");
      delay(5000);
    }
  }
  Serial.println("Connected to MQTT");
}

/**
 * Se revisa si hay mensajes MQTT
*/
void HandleMqtt() {
  HandleWiFi(); // Para que no se desconecte el WiFi
  if (!mqttClient.connected()) {
    Serial.println("Fallo en la conexión MQTT. Reconectando...");
    ConnectMqtt();
  }
  mqttClient.loop();
}
