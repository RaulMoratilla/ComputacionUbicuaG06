// Declaración de estructuras

// Luces normales
struct Luz {
  int pin;            // Pin de la luz
  int tiempoCambio;   // Momento en el que se puede cambiar el estado
  char* topic;        // Topic al que se suscribe
};

// Equivalente a farolas
struct LuzNoche {
  int pin;                    // Pin de la luz
  int tiempoCambio;           // Momento en el que se puede cambiar el estado
  bool noche;                 // Si es de noche o no
  char* topicLuz;             // Topic al que se suscribe (luz)
  char* topicMovimiento;      // Topic al que se suscribe (movimiento)
};

// Sensor de temperatura y humedad
struct SenTH {
  int pin;                        // Pin del sensor
  float calibracionTemperatura;   // Número por el que se multiplica la temperatura para calibrar
  float calibracionHumedad;       // Número por el que se multiplica la humedad para calibrar
  int valorMinimoTemperatura;     // Valor mínimo de la temperatura con el que se alerta
  int valorMinimoHumedad;         // Valor mínimo de la humedad con el que se alerta
  int valorMaximoTemperatura;     // Valor máximo de la temperatura con el que se alerta
  int valorMaximoHumedad;         // Valor máximo de la humedad con el que se alerta
  int timeoutTemperatura;         // Tiempo que se tarda en volver a actualizar el led que reciba el mensaje
  int timeoutHumedad;             // Tiempo que se tarda en volver a actualizar el led que reciba el mensaje
  char* topicTemp;                // Topic al que publica (temperatura)
  char* topicHum;                 // Topic al que publica (humedad)
};

// Sensor de ultrasonidos
struct SenUS {
  int pinTrigger;           // Pin del echo del sensor
  int pinEcho;              // Pin del trigger del sensor
  float calibracionCM;      // Número por el que se multiplican los CM para calibrar
  int valorMinimoDistancia; // Valor mínimo de la distancia con el que se alerta
  int valorMaximoDistancia; // Valor máximo de la distancia con el que se alerta
  int timeout;              // Tiempo que se tarda en volver a actualizar el led que reciba el mensaje
  char* topic;              // Topic al que publica
};

// Sensor básico
struct Sensor {
  int pin;          // Pin del sensor
  int calibracion;  // Número por el que se multiplica el valor para calibrar
  int valorMinimo;  // Valor mínimo con el que se alerta
  int valorMaximo;  // Valor máximo con el que se alerta
  int timeout;      // Tiempo que se tarda en volver a actualizar el led que reciba el mensaje
  char* topic;      // Topic al que publica
};

// Listas de Luces. Las variables N_LUZ_ deben estar declaradas

Luz lucesTemp[N_LUZ_TEMP];
Luz lucesHum[N_LUZ_HUM];
LuzNoche lucesNoche[N_LUZ_NOCHE];
Luz lucesLluvia[N_LUZ_LLUVIA];
Luz lucesPasoCebra[N_LUZ_CEBRA];
Luz lucesHorario[N_LUZ_HORARIO];

// PINMODES

/**
 *  Pines de sensor común como INPUT
*/
void iniciarPinSensor(Sensor s) { 
  pinMode(s.pin, INPUT);
}

/**
 * Pines de sensor DHT como INPUT
*/
void iniciarPinSensorTH(SenTH s) {
  pinMode(s.pin, INPUT);
}

/**
 * Pines de sensor ultrasonidos como INPUT
*/
void iniciarPinSensorUS(SenUS s) { 
  pinMode(s.pinTrigger, OUTPUT);
  pinMode(s.pinEcho, INPUT);
}

/**
 * Pines de luces como OUTPUT
*/
void iniciarPinLuces(Luz luces[], int tamArray) {
  for (int i = 0 ; i < tamArray ; i++) {
    pinMode(luces[i].pin, OUTPUT);
  }
}

/**
 * Pines de luces noche como OUTPUT
*/
void iniciarPinLucesNoche(LuzNoche luces[], int tamArray) {
  for (int i = 0 ; i < tamArray ; i++) {
    pinMode(luces[i].pin, OUTPUT);
  }
}

// Writes

/**
 * Enciende o apaga una luz si encender
*/
void gestionarLuzD(Luz l, bool encender) { 
  if (encender) {
    digitalWrite(l.pin, HIGH);
  }
  else {
    digitalWrite(l.pin, LOW);
  }
}

/**
 * Enciende una luz analógicamente con potencia
*/
void gestionarLuzA(Luz l, int potencia) {
  analogWrite(l.pin, potencia);
}

/**
 * Pone las luces cuyo topic sea "topic" de forma analógica con la potencia solicitada
*/
void gestionarLucesA(Luz luces[], int tamArray, int potencia, char* topic, int delayCambio = 0) {
  for (int i = 0 ; i < tamArray ; i++) {
    if (strstr(luces[i].topic, topic) && luces[i].tiempoCambio < millis()) {  // Se comprueba el topic y que se pueda cambiar el estado
      analogWrite(luces[i].pin, potencia);
      luces[i].tiempoCambio = millis() + delayCambio;
    }
  }
}

/**
 * Enciende o apaga las luces cuyo topic sea "topic" de forma digital si encender
*/
void gestionarLucesD(Luz luces[], int tamArray, bool encender, char* topic, int delayCambio = 0) {
  for (int i = 0  ; i < tamArray ; i++) {
    if (strstr(luces[i].topic, topic)) {
      if (!encender && luces[i].tiempoCambio < millis() || encender) {  // Se comprueba el topic y que se pueda cambiar el estado
        digitalWrite(luces[i].pin, encender);
        luces[i].tiempoCambio = millis() + delayCambio;
      }
    }
  }
}

/**
 * Pone las luces de noche cuyo topic sea "topic" de forma analógica con la potencia solicitada
*/
void gestionarLucesNocheA(LuzNoche luces[], int tamArray, int potencia, char* topic, int delayCambio = 0) {
  for (int i = 0 ; i < tamArray ; i++) {
    if (strstr(luces[i].topicLuz, topic)) {
      if (potencia > 0 && luces[i].tiempoCambio < millis()) {
        analogWrite(luces[i].pin, potencia);
        luces[i].noche = true;
        luces[i].tiempoCambio = millis() + delayCambio;        
      }
      else if (potencia == 0){
        analogWrite(luces[i].pin, potencia);
        luces[i].noche = false;
        luces[i].tiempoCambio = millis() + delayCambio;
      }
    }
  }
}

/**
 * Pone las luces de movimiento cuyo topic sea "topic" de forma analógica con la potencia solicitada
*/
void gestionarLucesMovimientoA(LuzNoche luces[], int tamArray, int potencia, char* topic, int delayCambio) {
  for (int i = 0 ; i < tamArray ; i++) {
    if (strstr(luces[i].topicMovimiento, topic) && luces[i].noche) {
      analogWrite(luces[i].pin, potencia);
      luces[i].tiempoCambio = millis() + delayCambio;
    }
  }
}

// Acciones al llegar MQTTS

/**
 * Enciende o apaga las luces de temperatura según dato
*/ 
void gestionLuzTemperatura(int dato, int valorMinimo, int valorMaximo, int timeout, char* topic) {
  gestionarLucesD(lucesTemp, N_LUZ_TEMP, dato <= valorMinimo || dato >= valorMaximo, topic, timeout); 
}

/**
 * Enciende o apaga las luces de humedad según dato
*/
void gestionLuzHumedad(int dato, int valorMinimo, int valorMaximo, int timeout, char* topic) {
  gestionarLucesD(lucesHum, N_LUZ_HUM, dato <= valorMinimo || dato >= valorMaximo, topic, timeout); 
}

/**
 * Enciende o apaga las luces de noche (farolas) según dato
*/
void gestionLuzNoche(int dato, int valorMinimo, int valorMaximo, int timeout, char* topic) {
  if (dato <= valorMinimo || dato >= valorMaximo) {
    gestionarLucesNocheA(lucesNoche, N_LUZ_NOCHE, 50, topic, timeout);
  } 
  else {
    gestionarLucesNocheA(lucesNoche, N_LUZ_NOCHE, 0, topic, timeout);
  }
}

/**
 * Potencia las luces de noche (farolas) si hay movimiento
*/
void gestionarLuzMovimiento(int dato, int valorMinimo, int valorMaximo, int timeout, char* topic) {
  if (dato <= valorMinimo || dato >= valorMaximo) {
    gestionarLucesMovimientoA(lucesNoche, N_LUZ_NOCHE, 255, topic, timeout);
  }
}

/**
 * Enciende o apaga las luces de lluvia según dato
*/
void gestionLuzLluvia(int dato, int valorMinimo, int valorMaximo, int timeout, char* topic) {
  gestionarLucesD(lucesLluvia, N_LUZ_LLUVIA, dato <= valorMinimo || dato >= valorMaximo, topic, timeout);      
}

/**
 * Enciende o apaga las luces de paso de cebra según dato
*/
void gestionPasoCebra(int dato, int valorMinimo, int valorMaximo, int timeout, char* topic) {
  if (dato <= valorMinimo || dato >= valorMaximo) {
    gestionarLucesD(lucesPasoCebra, N_LUZ_CEBRA, true, topic, timeout);
  } else {
    gestionarLucesD(lucesPasoCebra, N_LUZ_CEBRA, false, topic, 0);
  }
}

/**
 * Enciende o apaga las luces de horario
*/
void gestionHorario(int dato, int valorMinimo, int valorMaximo, int timeout, char* topic) {
  gestionarLucesD(lucesHorario, N_LUZ_HORARIO, dato <= valorMinimo || dato >= valorMaximo, topic, timeout);
}

// MQTT

/**
 * Mensaje que se ejecuta al llegar un mensaje MQTT de un topic suscrito
*/
void OnMqttReceived(char* topic, byte* payload, unsigned int length) {
  
  String content = "";    // Se crea un string para almacenar el mensaje
  for (size_t i = 0; i < length; i++) {
      content.concat((char)payload[i]);
  }

  int tipo, dato, valorMinimo, valorMaximo, timeout;   // Se crea un int para almacenar el tipo del sensor y otro para el dato
  tipo = content.substring(0, content.indexOf(";")).toInt();
  content = content.substring(content.indexOf(";")+1, -1);
  dato = content.substring(0, content.indexOf(";")).toInt();
  content = content.substring(content.indexOf(";")+1, -1);
  valorMinimo = content.substring(0, content.indexOf(";")).toInt();
  content = content.substring(content.indexOf(";")+1, -1);
  valorMaximo = content.substring(0, content.indexOf(";")).toInt();
  timeout = content.substring(content.indexOf(";")+1, -1).toInt();

  // Se ejecuta la acción correspondiente al tipo de sensor
  switch (tipo) {
    case 0:
      gestionLuzTemperatura(dato, valorMinimo, valorMaximo, timeout, topic);
      break;
    case 1:
      gestionLuzHumedad(dato, valorMinimo, valorMaximo, timeout, topic);
      break;
    case 2:
      gestionLuzNoche(dato, valorMinimo, valorMaximo, timeout, topic);
      break;
    case 3:  
      gestionarLuzMovimiento(dato, valorMinimo, valorMaximo, timeout, topic);
      break;
    case 4:
      gestionLuzLluvia(dato, valorMinimo, valorMaximo, timeout, topic);
      break;
    case 5:
      gestionPasoCebra(dato, valorMinimo, valorMaximo, timeout, topic);
      break;
    case 6:
      gestionHorario(dato, valorMinimo, valorMaximo, timeout, topic);
      break;
  }
}

/**
 * Publica un mensaje MQTT en el topic indicado
*/
void PublisMqtt(int tipo, int data, char* topic, int valorMinimo, int valorMaximo, int timeout = 0) {
    String payload = "";
    payload = String(tipo);
    payload.concat(";");
    payload.concat((String)data);
    payload.concat(";");
    payload.concat((String)valorMinimo);
    payload.concat(";");
    payload.concat((String)valorMaximo);
    payload.concat(";");
    payload.concat((String)timeout);

    // Se publica un mensaje con formato tiposensor-dato
    mqttClient.publish(topic, String(payload).c_str());
}

// Control Sensores

/**
 * Calcula la distancia en cm a la que está el objeto más próximo
 * al sensor Ultrasonidos
*/
int getDistanciaCM(SenUS s) {
  int duracion;
  digitalWrite(s.pinTrigger, LOW);  //para generar un pulso limpio ponemos a LOW 4us
  delayMicroseconds(4);
  digitalWrite(s.pinTrigger, HIGH);  //generamos Trigger (disparo) de 10us
  delayMicroseconds(10);
  digitalWrite(s.pinTrigger, LOW);
  duracion = pulseIn(s.pinEcho, HIGH);  //medimos el tiempo entre pulsos, en microsegundos
  return s.calibracionCM * duracion * 10 / 292 / 2;   //convertimos a distancia, en cm
}

/**
 * Controla la temperatura con el sensor y publica los datos
*/
void controlarTemperatura(SenTH s, DHT dht) {
  int temperatura = dht.readTemperature()*s.calibracionTemperatura;
  PublisMqtt(0, temperatura, s.topicTemp, s.valorMinimoTemperatura, s.valorMaximoTemperatura, s.timeoutTemperatura);
}

/**
 * Controla la humedad con el sensor y publica los datos
*/
void controlarHumedad(SenTH s, DHT dht) {
  int humedad = dht.readHumidity()*s.calibracionHumedad;
  PublisMqtt(1, humedad, s.topicHum, s.valorMinimoHumedad, s.valorMaximoHumedad, s.timeoutHumedad);
}

/**
 * Controla la luz con el sensor y publica los datos
*/
void controlarLuz(Sensor s) {
  int nivelLuz = analogRead(s.pin)*s.calibracion;
  PublisMqtt(2, nivelLuz, s.topic, s.valorMinimo, s.valorMaximo, s.timeout);
}

/**
 * Controla el movimiento con el sensor y publica los datos
*/
void controlarMovimiento(Sensor s) {
  int movimiento = digitalRead(s.pin)*s.calibracion;
  PublisMqtt(3, movimiento, s.topic, s.valorMinimo, s.valorMaximo, s.timeout);
}

/**
 * Controla la lluvia con el sensor y publica los datos
*/
void controlarLluvia(Sensor s) {
  int lluvia = !(digitalRead(s.pin)*s.calibracion);
  PublisMqtt(4, lluvia, s.topic, s.valorMinimo, s.valorMaximo, s.timeout);
}

/**
 * Controla el paso de cebra con el sensor y publica los datos
*/
void controlarPasoCebra(SenUS s) {
  int distanciaCm = getDistanciaCM(s);
  PublisMqtt(5, distanciaCm, s.topic, s.valorMinimoDistancia, s.valorMaximoDistancia, s.timeout);
}
