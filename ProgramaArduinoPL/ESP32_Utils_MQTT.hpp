
void SuscribeMqtt() {
  mqttClient.subscribe(luzHum[0].topic);
  mqttClient.subscribe(luzTemp[0].topic);
  mqttClient.subscribe(luzLluvia[0].topic);
  mqttClient.subscribe("maqueta/maqueta/calle1/sensores/sLuz");
  mqttClient.subscribe("maqueta/maqueta/calle1/sensores/movilidad");
  mqttClient.subscribe(luzPasoCebra[0].topic);
  mqttClient.subscribe(luzHorario[0].topic);
}


void InitMqtt() {
  Serial.print("Connecto to MQTT - ");
  Serial.print(MQTT_BROKER_ADRESS);
  Serial.print(" - ");
  Serial.println(MQTT_PORT);
  mqttClient.setServer(MQTT_BROKER_ADRESS, MQTT_PORT);
  SuscribeMqtt();
  mqttClient.setCallback(OnMqttReceived);
}

void ConnectMqtt() {
  while (!mqttClient.connected()) {
    Serial.println("Starting MQTT connection...");
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
}

void HandleMqtt() {
  if (!mqttClient.connected()) {
    ConnectMqtt();
  }
  mqttClient.loop();
}
