#include <WiFi.h>

#define WIFI_SSID ""
#define WIFI_PASSWORD ""

int pinStLight1 = ;
int pinStLight2 = ;
int pinStLight3 = ;
int pinPrecLight1 = ;
int pinPrecLight2 = ;
int pinTempLight = ;
int pinClimateLight = ;

int light = 0;
int infrarred1 = 0;
int infrarred2 = 0;
int infrarred3 = 0;
int infrarred4 = 0;
int temperature = 0;
int rain = 0;

int pinLightSensor = ;
int pinInfrarred1 = ;
int pinInfrarred2 = ;
int pinInfrarred3 = ;
int pinInfrarred4 = ;
int pinTemp_sensor = ;
int pinRain_sensor = ;

const char* mqttUser = "mqttuser";
const char* mqttPassword = "mqttpass";

#define st1_cross1 "st1/z_cross1"
#define st1_cross2 "st1/z_cross2"
#define st1_humidity "st1/humidity"
#define st1_temperature "st1/temperature"
#define st1_light "st1/light"
#define st2_light "st2/light"
#define st3_light "st3_light"

WiFiClient espClient;
PubSubClient client(espClient);


void setup() {
  // put your setup code here, to run once:

  pinMode(pinStLight1, OUTPUT);
  pinMode(pinStLight2, OUTPUT);
  pinMode(pinStLight3, OUTPUT);
  pinMode(pinPrecLight1, OUTPUT);
  pinMode(pinPrecLight2, OUTPUT);
  pinMode(pinTempLight, OUTPUT);
  pinMode(pinClimateLight, OUTPUT);

  pinMode(pinStLight1, INPUT);
  pinMode(pinStLight2, INPUT);
  pinMode(pinStLight3, INPUT);
  pinMode(pinPrecLight1, INPUT);
  pinMode(pinPrecLight2, INPUT);
  pinMode(pinTempLight, INPUT);
  pinMode(pinClimateLight, INPUT);

  Serial.begin(9600);

  initWifi();
  initMQTTServer();

}

void loop() {
  // put your main code here, to run repeatedly:
  light = analogRead(light)*X;
  infrarred1 = analogRead(infrarred1)*X;
  infrarred2 = analogRead(infrarred2)*X;
  infrarred3 = analogRead(infrarred3)*X;
  infrarred4 = analogRead(infrarred4)*X;
  temperature = analogRead(temperature)*X;
  rain = analogRead(rain)*X;
}


void initWifi() {
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
  }
  Serial.println("Connected to the WiFi");
}

void initMQTTServer() {
  client.setServer(mqttServer, mqttPort);
  while (!client.connected()) {
    if (client.connect("ESP32Client", mqttUser, mqttPassword)) {
      Serial.println("Connected to MQTT");
    } else {
      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);
    }
  }
  client.publish("esp/test", "Hello from ESP32");
}