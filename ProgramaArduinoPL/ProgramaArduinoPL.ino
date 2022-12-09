#include <analogWrite.h>
#include <WiFi.h>
#include <PubSubClient.h>

#include "DHT.h"

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
#include "ESP32_Utils.hpp"
//#include "ESP32_Utils_MQTT.hpp"
#include "libControlViasPublicas.hpp"
//#include "MQTT.hpp"

void setup() {

  luzTemp[0].pin = 4;
  luzTemp[0].topic = "maqueta/maqueta/calle1/sensores/sTemp";
  
  luzHum[0].pin = 4;
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

  //SuscribeMqtt(luzTH);
  //SuscribeMqtt(luzNoche);

  temperatura = 15.6;
  humedad = 50;
  nivelLuz = 150;
  msRevNoche = millis();
  lluvia = 0;
  noche = false;

  //SPIFFS.begin();
  //ConnectWiFi_STA();
  //InitMqtt();
  Serial.begin(115200);

}

void loop() {
  controlarHumedad();
  controlarMovimiento();
  delay(1000);
  controlarLuz();
  controlarMovimiento();
  delay(1000);
  controlarTemperatura();
  controlarMovimiento();
  delay(1000);
  controlarLluvia();
  controlarMovimiento();
  delay(1000);
  //HandleMqtt();
  //PublisMqtt(millis());
}
