#include <analogWrite.h>
#include <WiFi.h>
#include <PubSubClient.h>

#include "DHT.h"

#define DHTPIN 15     // Pin donde está conectado el sensor

#define DHTTYPE DHT22   // Sensor DHT22

DHT dht(DHTPIN, DHTTYPE);
#include "config.h"
#include "ESP32_Utils.hpp"
//#include "ESP32_Utils_MQTT.hpp"
#include "libControlViasPublicas.hpp"
//#include "MQTT.hpp"

void setup() {
  
  luzTH[0].pin = 4;
  luzTH[0].topic = "maqueta/maqueta/calle1/sensores/sTemp";
  
  senTH[0].pin = 15;
  senTH[0].topic = "maqueta/maqueta/calle1/sensores/sTemp";
  senTH[1].pin = 15;
  senTH[1].topic = "maqueta/maqueta/calle1/sensores/sTemp";

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


  iniciarPinSensor(senTH, 2);
  iniciarPinSensor(senLuz, 1);
  iniciarPinSensor(senLluvia, 1);
  iniciarPinSensor(senMovimiento, 1);
  iniciarPinLuz(luzTH, 1);
  iniciarPinLuz(luzNoche, 1);
  iniciarPinLuz(luzLluvia, 1);
   
  dht.begin();

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
  Serial.begin(9600);

}

void loop() {
  controlarHumedad(senTH[0], dht);
  controlarLucesMovimiento(senMovimiento[0]);
  delay(1000);
  controlarLucesNoche(senLuz[0]);
  controlarLucesMovimiento(senMovimiento[0]);
  delay(1000);
  controlarTemperatura(senTH[0], dht);
  controlarLucesMovimiento(senMovimiento[0]);
  delay(1000);
  controlarLucesLluvia(senLluvia[0]);
  controlarLucesMovimiento(senMovimiento[0]);
  delay(1000);
  //HandleMqtt();
  //PublisMqtt(millis());
}
