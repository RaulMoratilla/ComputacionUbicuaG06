struct luz {
  int pin;
  String topic;
};
typedef struct luz Luz;

struct sensor {
  int pin;
  int correccion;
  String topic;
};
typedef struct sensor Sensor;

float temperatura;
float humedad;
int nivelLuz;
int msRevNoche;
int lluvia;
bool noche;

Luz luzTH[1];
Sensor senTH[2];

Luz luzNoche[1];
Sensor senLuz[1];

Luz luzLluvia[1];
Sensor senLluvia[1];

Sensor senMovimiento[1];

void gestionarLuzD(bool encender, Luz l) {
  if (encender) {
    digitalWrite(l.pin, HIGH);
  }
  else {
    digitalWrite(l.pin, LOW);
  }
}

void gestionarLuzA(int potencia, Luz l) {
  analogWrite(l.pin, potencia);
}

void gestionarLuces(int potencia, Luz luces[], int tamArray) {
  for (int i ; i < tamArray ; i++) {
    analogWrite(luces[i].pin, potencia);
  }
}

void gestionLuzTemperaturaHumedad(int tipo, int dato) {
  if (tipo == 0) {
    temperatura = dato;
  } else {
    humedad = dato;
  }
  bool encender = temperatura <= 0 || humedad > 95;
  for (int i ; i < sizeof(luzTH)/sizeof(luzTH[0]) ; i++) {
    if (encender) {
      gestionarLuzA(255, luzTH[i]);      
    } else {
      gestionarLuzA(0, luzTH[i]);
    }   
  }
}

void gestionLuzNoche(int dato) {
  nivelLuz = dato;
  if (nivelLuz == 0) {
    if (msRevNoche + 10000 < millis()) {
      for (int i ; i < sizeof(luzNoche)/sizeof(luzNoche[0]) ; i++) {
        gestionarLuzA(50, luzNoche[i]);
      } 
    }
  } 
  else if (noche) {
    for (int i ; i < sizeof(luzNoche)/sizeof(luzNoche[0]) ; i++) {
      gestionarLuzA(0, luzNoche[i]);
    } 
  }
  
  noche = nivelLuz == 0;
}

void gestionLuzLluvia(int dato) {
  lluvia = dato;
  bool encender = dato == HIGH;
  for (int i ; i < sizeof(luzLluvia)/sizeof(luzLluvia[0]) ; i++) {
    if (encender) {
      gestionarLuzA(255, luzLluvia[i]);      
    } else {
      gestionarLuzA(0, luzLluvia[i]);
    }
  }
}

void llamada(int tipo, int dato, int tiempo) {
  switch (tipo) {
      case 0:
        gestionLuzTemperaturaHumedad(tipo, dato);
        break;
      case 1:
        gestionLuzTemperaturaHumedad(tipo, dato);
        break;
      case 2:
        gestionLuzNoche(dato);
        break;
      case 3:
        if (dato == 1 && noche) {
          gestionarLuces(255, luzNoche, 1);
          msRevNoche = tiempo;
        } else if (msRevNoche + 10000 < millis()) {
          gestionLuzNoche(nivelLuz);
        }
        break;
      case 4:
        gestionLuzLluvia(dato);
        break;
    }
}

void PublisMqtt(int tipo, int data, int tiempo, String topic) {
    /*payload = "";
    payload = String(tipo);
    payload.concat("-");
    payload.concat((String)data);
    payload.concat("-");
    payload.concat((String)tiempo);
    mqttClient.publish(topic, (char*)payload.c_str());*/
    Serial.print(tipo);
    Serial.print("-");
    Serial.print(data);
    Serial.print("-");
    Serial.println(tiempo);
    llamada(tipo, data, tiempo);
}


void iniciarPinSensor(Sensor sensores[], int tamArray) {
  for (int i ; i < tamArray ; i++) {
    pinMode(sensores[i].pin, INPUT);
  }
}

void iniciarPinLuz(Luz luces[], int tamArray) {
  for (int i ; i < tamArray ; i++) {
    pinMode(luces[i].pin, OUTPUT);
  }
}

void controlarHumedad(Sensor s, DHT dht) {
  Serial.print("Humedad: ");
  float h = dht.readHumidity();
  PublisMqtt(1, h, millis(), s.topic);
}

void controlarTemperatura(Sensor s, DHT dht) {
  Serial.print("Temperatura: ");
  float t = dht.readTemperature()*0.97;
  PublisMqtt(0, t, millis(), s.topic);
}

void controlarLucesNoche(Sensor s) {
  Serial.print("Luz: ");
  int nivelLuz = analogRead(s.pin);
  PublisMqtt(2, nivelLuz, millis(), s.topic);
}

void controlarLucesLluvia(Sensor s) {
  Serial.print("Lluvia: ");
  int lluvia = digitalRead(s.pin);
  PublisMqtt(4, lluvia, millis(), s.topic);
}

void controlarLucesMovimiento(Sensor s) {
  Serial.print("Movimiento: ");
  int movimiento = digitalRead(s.pin);
  PublisMqtt(3, movimiento, millis(), s.topic);
}
