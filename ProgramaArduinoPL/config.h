// Wifi Settings

const char* ssid = "WifiRaul";
const char* password = "claveUbicua";
const char* hostname = "ESP32_1";

//MQTT Settings

const char* MQTT_BROKER_ADRESS = "mqttIp";
const uint16_t MQTT_PORT = 1883;
const char* MQTT_CLIENT_NAME = "ESP32Client_1";

//Server Settings

IPAddress ip(192, 168, 1, 200);
IPAddress gateway(192, 168, 1, 1);
IPAddress subnet(255, 255, 255, 0);
