struct Luz {
  int pin;
  char* topic;
};

struct SenTH {
  int pin;
  char* topicTemp;
  char* topicHum;
};

struct SenUS {
  int pinTrigger;
  int pinEcho;
  char* topic;
};

struct Sensor {
  int pin;
  int correccion;
  char* topic;
};

int msRevPasoCebra;
float temperatura;
float humedad;
int nivelLuz;
int msRevNoche;
int lluvia;
bool noche;

Luz luzTemp[N_LUZ_TEMP];
Luz luzHum[N_LUZ_HUM];
SenTH senTH[N_SEN_TH];

Luz luzNoche[N_LUZ_NOCHE];
Sensor senLuz[N_SEN_NOCHE];

Luz luzLluvia[N_LUZ_LLUVIA];
Sensor senLluvia[N_SEN_LLUVIA];

Sensor senMovimiento[N_SEN_MOV];

SenUS senUS;
Luz luzPasoCebra[N_LUZ_CEBRA];

Luz luzHorario[N_LUZ_HORARIO];

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

void gestionarLucesA(int potencia, Luz luces[], int tamArray) {
  for (int i = 0 ; i < tamArray ; i++) {
    analogWrite(luces[i].pin, potencia);
  }
}

void gestionarLucesD(bool encender, Luz luces[], int tamArray) {
  for (int i = 0  ; i < tamArray ; i++) {
    if (encender) {
      digitalWrite(luces[i].pin, HIGH);
    } else {
      digitalWrite(luces[i].pin, LOW);
    }
  }
}

void gestionLuzTemperatura(int dato) {
  for (int i = 0 ; i < N_LUZ_TEMP ; i++) {
    gestionarLucesD(dato < 0, luzTemp, N_LUZ_TEMP); 
  }
}

void gestionLuzHumedad(int dato) {
  for (int i = 0 ; i < N_LUZ_HUM ; i++) {
    gestionarLucesD(dato > 95, luzHum, N_LUZ_HUM); 
  }
}

void gestionLuzNoche(int dato) {
  nivelLuz = dato;
  if (nivelLuz == 0) {
    if (msRevNoche + 10000 < millis()) {
      for (int i = 0 ; i < N_LUZ_NOCHE ; i++) {
        gestionarLuzA(50, luzNoche[i]);
      } 
    }
  } 
  else if (noche) {
    for (int i = 0 ; i < N_LUZ_NOCHE ; i++) {
      gestionarLuzA(0, luzNoche[i]);
    } 
  }
  
  noche = nivelLuz == 0;
}

void gestionLuzLluvia(int dato) {
  lluvia = dato;
  bool encender = dato == LOW;
  for (int i = 0 ; i < N_LUZ_LLUVIA ; i++) {
    if (encender) {
      gestionarLuzA(255, luzLluvia[i]);      
    } else {
      gestionarLuzA(0, luzLluvia[i]);
    }
  }
}

void gestionPasoCebra(int dato) {
  if (dato < 300) {
    gestionarLucesD(true, luzPasoCebra, N_LUZ_CEBRA);
    msRevPasoCebra = millis();
  } else if (msRevPasoCebra + 10000 < millis()) {
    gestionarLucesD(false, luzPasoCebra, N_LUZ_CEBRA);
  }
}

void gestionHorario(int dato) {
  if (dato == 1) {
    gestionarLucesD(true, luzHorario, N_LUZ_HORARIO);
  } else {
    gestionarLucesD(false, luzHorario, N_LUZ_HORARIO);
  }
}

void OnMqttReceived(char* topic, byte* payload, unsigned int length) {
  
  String content = "";
  for (size_t i = 0; i < length; i++) {
      content.concat((char)payload[i]);
  }

  int tipo, dato, tiempo;
  tipo = content.substring(0, content.indexOf("-")).toInt();
  String aux = content.substring(content.indexOf("-")+1, -1);
  dato = aux.substring(0, aux.indexOf("-")).toInt();
  tiempo = aux.substring(aux.indexOf("-")+1, -1).toInt();

  Serial.print("Llega: ");
  Serial.println(content);

  switch (tipo) {
    case 0:
      gestionLuzTemperatura(dato);
      break;
    case 1:
      gestionLuzHumedad(dato);
      break;
    case 2:
      gestionLuzNoche(dato);
      break;
    case 3:  
      if (dato == 1 && noche) {
        gestionarLucesA(255, luzNoche, N_LUZ_NOCHE);
        msRevNoche = tiempo;
      } else if (msRevNoche + 10000 < millis()) {
        gestionLuzNoche(nivelLuz);
      }
      break;
    case 4:
      gestionLuzLluvia(dato);
      break;
    case 5:
      gestionPasoCebra(dato);
      break;
    case 6:
      gestionHorario(dato);
      break;

  }

}

void PublisMqtt(int tipo, int data, int tiempo, char* topic) {
    String payload = "";
    payload = String(tipo);
    payload.concat("-");
    payload.concat((String)data);
    payload.concat("-");
    payload.concat((String)tiempo);

    mqttClient.publish(topic, String(payload).c_str());
    //llamada(tipo, data, tiempo);
}


void iniciarPinSensor(Sensor sensores[], int tamArray) {
  for (int i = 0 ; i < tamArray ; i++) {
    pinMode(sensores[i].pin, INPUT);
  }
}

void iniciarPinSensorTH(SenTH sensores[], int tamArray) {
  for (int i = 0 ; i < tamArray ; i++) {
    pinMode(sensores[i].pin, INPUT);
  }
}

void iniciarPinSensorUS(SenUS s) {
  pinMode(s.pinTrigger, OUTPUT);
  pinMode(s.pinEcho, INPUT);
}

void iniciarPinLuz(Luz luces[], int tamArray) {
  for (int i = 0 ; i < tamArray ; i++) {
    pinMode(luces[i].pin, OUTPUT);
  }
}

void controlarHumedad() {
  for (int i = 0 ; i < N_SEN_TH ; i++) {
    float h = dht.readHumidity();
    PublisMqtt(1, h, millis(), senTH[i].topicHum);
  }
}

void controlarTemperatura() {
  for (int i = 0 ; i < N_SEN_TH ; i++) {
    float t = dht.readTemperature()*0.97;
    PublisMqtt(0, t, millis(), senTH[i].topicTemp);
  }
}

void controlarLuz() {
  for (int i = 0 ; i < N_SEN_NOCHE ; i++) {
    int nivelLuz = analogRead(senLuz[i].pin);
    PublisMqtt(2, nivelLuz, millis(), senLuz[i].topic);
  }
}

void controlarLluvia() {
  for (int i = 0 ; i < N_SEN_LLUVIA ; i++) {
    int lluvia = digitalRead(senLluvia[i].pin);
    PublisMqtt(4, lluvia, millis(), senLluvia[i].topic);
  }
}

void controlarMovimiento() {
  for (int i = 0 ; i < N_SEN_MOV ; i++) {
    int movimiento = digitalRead(senMovimiento[i].pin);
    PublisMqtt(3, movimiento, millis(), senMovimiento[i].topic);
  }
}

void controlarPasoCebra() {
  long duracion, distanciaCm;
   
  digitalWrite(senUS.pinTrigger, LOW);  //para generar un pulso limpio ponemos a LOW 4us
  delayMicroseconds(4);
  digitalWrite(senUS.pinTrigger, HIGH);  //generamos Trigger (disparo) de 10us
  delayMicroseconds(10);
  digitalWrite(senUS.pinTrigger, LOW);
  
  duracion = pulseIn(senUS.pinEcho, HIGH);  //medimos el tiempo entre pulsos, en microsegundos
  
  distanciaCm = duracion * 10 / 292 / 2;   //convertimos a distancia, en cm

  PublisMqtt(5, distanciaCm, millis(), senUS.topic);
  
}
