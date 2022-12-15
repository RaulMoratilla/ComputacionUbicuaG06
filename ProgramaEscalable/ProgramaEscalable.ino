#include <analogWrite.h>
#include <WiFi.h>
#include <PubSubClient.h>
#include <DHT.h>

WiFiClient espClient;
PubSubClient mqttClient(espClient);

#include "config.h"
#include "libControlViasPublicas.hpp"
#include "ESP32_Utils.hpp"
#include "ESP32_Utils_MQTT.hpp"

#define DHTTYPE DHT22   // Sensor DHT22

DHT dht(15, DHTTYPE);   // Para el sensor de Temperatura y Humedad
SenTH senTH;            // Sensor de Temperatura y Humedad
SenUS senUS;            // Sensor de Ultrasonidos
Sensor senMovCalle1, senMovCalle2, senLuz, senLluvia; // Sensores de movimiento, luz y lluvia 

int tiempoUltAccesoTH;  // Tiempo en el que se ha accedido por última vez al sensor de Temperatura y Humedad
bool ultimo;            // Si el último ha sido el sensor de temperatura o el de humedad

void setup() {

  Serial.begin(115200);   // Conexión serie

  // Inicialización de sensores
  senTH = {15, 0.97, 1, 0, 5, 35, 95, 0, 0, "ciudad1/zona1/datos/temperatura", "ciudad1/zona1/datos/humedad"};
  senUS = {12, 34, 1, 6, 999999, 10000, "ciudad1/zona1/calles/calle1/pasosCebra/pasoCebra1"};
  senMovCalle1 = {13, 1, -1, 1, 10000, "ciudad1/zona1/calles/calle1/movimiento"};
  senMovCalle2 = {32, 1, -1, 1, 10000, "ciudad1/zona1/calles/calle2/movimiento"};
  senLuz = {33, 1, 0, 999999, 0, "ciudad1/zona1/datos/luz"};
  senLluvia = {25, 1, -1, 1, 0, "ciudad1/zona1/datos/lluvia"};
  dht.begin();

  // Inicialización de pines de sensores
  iniciarPinSensorTH(senTH);
  iniciarPinSensorUS(senUS);
  iniciarPinSensor(senMovCalle1);
  iniciarPinSensor(senMovCalle2);
  iniciarPinSensor(senLuz);
  iniciarPinSensor(senLluvia);


  // Inicialización de luces
  lucesTemp[0] = {4, 0, "ciudad1/zona1/datos/temperatura"};
  lucesHum[0] = {16, 0, "ciudad1/zona1/datos/humedad"};
  lucesNoche[0] = {17, 0, false, "ciudad1/zona1/datos/luz", "ciudad1/zona1/calles/calle1/movimiento"};
  lucesNoche[1] = {23, 0, false, "ciudad1/zona1/datos/luz", "ciudad1/zona1/calles/calle2/movimiento"};
  lucesLluvia[0] = {19, 0, "ciudad1/zona1/datos/lluvia"};
  lucesPasoCebra[0] = {22, 0, "ciudad1/zona1/calles/calle1/pasosCebra/pasoCebra1"};
  lucesHorario[0] = {21, 0, "ciudad1/zona1/calles/calle1/horarioConflictivo"};
 

  // Inicialización de pines de luces
  iniciarPinLuces(lucesTemp, N_LUZ_TEMP);
  iniciarPinLuces(lucesHum, N_LUZ_HUM);
  iniciarPinLucesNoche(lucesNoche, N_LUZ_NOCHE);
  iniciarPinLuces(lucesLluvia, N_LUZ_LLUVIA);
  iniciarPinLuces(lucesPasoCebra, N_LUZ_CEBRA);
  iniciarPinLuces(lucesHorario, N_LUZ_HORARIO); 

  // Inicialización de WiFi y MQTT
  ConnectWiFi_STA();
  InitMqtt();

  // Inicialización de variables
  tiempoUltAccesoTH = millis();
  ultimo = false;

  // Espera para que se conecte todo
  delay(10000);

}

void loop() {
  // Cada vez que se envía información se vigila si ha llegado algo
  HandleMqtt();

  // Las mediciones de Luz Temperatura y lluvia las realizaremos 1 vez cada 2 minutos
  // Las mediciones de Temperatura y Luz se deben realizar con un margen de 2 segundos por ello
  // el uso de la variable ultimo. Cada minuto se medira una distinta
  if (millis() > tiempoUltAccesoTH + 60000) {
    if (ultimo) {
      controlarTemperatura(senTH, dht);
      HandleMqtt();
      controlarLluvia(senLluvia);
    }
    else {
      controlarHumedad(senTH, dht);
    }

    ultimo = !ultimo;
    tiempoUltAccesoTH = millis();    
    HandleMqtt();
  }

  controlarLuz(senLuz);
  HandleMqtt();
  controlarMovimiento(senMovCalle1);
  HandleMqtt();
  controlarMovimiento(senMovCalle2);
  HandleMqtt();
  controlarPasoCebra(senUS);
  HandleMqtt();

  // Se realizan mediciones cada medio segundo
  delay(500);
}
