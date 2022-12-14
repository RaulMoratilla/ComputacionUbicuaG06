
/**
 * Se conecta a una Wifi como cliente
*/
void ConnectWiFi_STA() {
  Serial.println("");
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  Serial.print("Connecting to WiFi");
  while (WiFi.status() != WL_CONNECTED) {
    delay(100);
    Serial.print('.');
}
  Serial.println("Connected to WiFi");
}

/**
 * Se revisa si hay conexión WiFi
*/
void HandleWiFi() {
  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("Fallo en la conexión WiFi. Reconectando...");
    ConnectWiFi_STA();
  }
}
