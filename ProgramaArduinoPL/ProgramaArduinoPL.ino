#include <analogWrite.h>
#include <WiFi.h>
#include <PubSubClient.h>

#include "DHT.h"

WiFiClient espClient;
PubSubClient mqttClient(espClient);

#define N_LUZ_HUM 1
#define N_LUZ_TEMP 1
#define N_SEN_TH 1
#define N_LUZ_LLUVIA 1
#define N_SEN_LLUVIA 1
#define N_LUZ_NOCHE 1
#define N_SEN_NOCHE 1
#define N_SEN_MOV 1

#define DHTPIN 15     // Pin donde está conectado el sensor
#define DHTTYPE DHT22   // Sensor DHT22
DHT dht(DHTPIN, DHTTYPE);

#include "config.h"
#include "libControlViasPublicas.hpp"
#include "MQTT.hpp"
#include "ESP32_Utils.hpp"
#include "ESP32_Utils_MQTT.hpp"

void setup() {

  Serial.begin(115200);

  luzTemp[0].pin = 4;
  luzTemp[0].topic = "maqueta/maqueta/calle1/sensores/sTemp";
  
  luzHum[0].pin = 16;
  luzHum[0].topic = "maqueta/maqueta/calle1/sensores/sHum";

  senTH[0].pin = 15;
  senTH[0].topicHum = "maqueta/maqueta/calle1/sensores/sHum";
  senTH[0].topicTemp = "maqueta/maqueta/calle1/sensores/sTemp";

  luzNoche[0].pin = 23;
  luzNoche[0].topic = "maqueta/maqueta/calle1/sensores/sLuz";

  senLuz[0].pin = 33;
  senLuz[0].topic = "maqueta/maqueta/calle1/sensores/sLuz";

  senLluvia[0].pin = 32;
  senLluvia[0].topic = "maqueta/maqueta/calle1/sensores/sLluvia";
  
  luzLluvia[0].pin = 19;
  luzLluvia[0].topic = "maqueta/maqueta/calle1/sensores/sLluvia";

  senMovimiento[0].pin = 13;
  senMovimiento[0].topic = "maqueta/maqueta/calle1/sensores/movilidad";

  dht.begin();

  iniciarPinSensorTH(senTH, 1);
  iniciarPinSensor(senLuz, 1);
  iniciarPinSensor(senLluvia, 1);
  iniciarPinSensor(senMovimiento, 1);
  iniciarPinLuz(luzTemp, 1);
  iniciarPinLuz(luzHum, 1);
  iniciarPinLuz(luzNoche, 1);
  iniciarPinLuz(luzLluvia, 1);

  temperatura = 15.6;
  humedad = 50;
  nivelLuz = 150;
  msRevNoche = millis();
  lluvia = 0;
  noche = false;

  ConnectWiFi_STA();
  InitMqtt();
}

void loop() {
  HandleMqtt();
  controlarHumedad();
  HandleMqtt();
  controlarMovimiento();
  HandleMqtt();
  delay(1000);
  controlarLuz();
  HandleMqtt();
  controlarMovimiento();
  HandleMqtt();
  delay(1000);
  controlarTemperatura();
  HandleMqtt();
  controlarMovimiento();
  HandleMqtt();
  delay(1000);
  controlarLluvia();
  HandleMqtt();
  controlarMovimiento();
  HandleMqtt();
  delay(1000);
  //PublisMqtt(millis());
}
