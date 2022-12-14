package com.ubicua.smartstreet;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ubicua.smartstreet.Data.Calle;
import com.ubicua.smartstreet.Data.HoraPunta;
import com.ubicua.smartstreet.Data.Zona;
import com.ubicua.smartstreet.Data.Sensor;

import org.json.JSONArray;
import org.json.JSONObject;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import android.os.Build; //para obtener el nombre del dispositivo
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SelectCalleActivity extends AppCompatActivity {

    private String tag = "SelectCalle";

    private int idCiudad =1;
    private int idZona;
    private int idCalle;

    private Spinner spinnerCalle;
    private Spinner spinnerZona;
    private Button buttonCalle;
    private Button buttonRecargar;
    private TextView temp;
    private TextView lluvi;
    private TextView humedo;
    private TextView f0;
    private TextView f1;
    private TextView f2;
    private TextView f3;
    private TextView f4;
    private TextView f5;
    private TextView f6;
    private TextView f7;
    private TextView f8;
    private TextView f9;
    private TextView f10;
    private TextView f11;
    private TextView f12;
    private TextView f13;
    private TextView f14;
    private TextView f15;
    private TextView f16;
    private TextView f17;
    private TextView f18;
    private TextView f19;
    private TextView f20;
    private TextView f21;
    private TextView f22;
    private TextView f23;
    private ImageView imagen;

    ArrayList<String> arrayCalle;
    ArrayList<String> arrayValores;
    private ArrayList<Calle> listCalle;
    private ArrayList<HoraPunta> arrayHoras;
    ArrayList<String> arrayZona;
    private ArrayList<Zona> listZona;
    private final Context context;
    private String nameCalle = "";

    private MqttAndroidClient client;
    private final static String CHANNEL_ID = "calleId";
    private final static int NOTIFICATION_ID=0;
    private String station = "";
    private String stationName = "";
    private TextView tvstationname;
    private TextView tvstationinfo;
    private JSONArray jsonValores;

    public SelectCalleActivity() {
        super();
        this.context = this;
    }

    protected void onCreate(Bundle savedInstanceState) {

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://192.168.151.14:1883", clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //If the connection is ok
                    Log.i(tag, "MQTT connected");
                    //Suscribe the topics
                    suscripcionTopics(idCiudad,idZona,idCalle);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.i(tag, "Error connecting MQTT");
                }
            });
        } catch (MqttException e) {e.printStackTrace();}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_calle);

        //Init the spinners and the buttons and textViews
        this.spinnerCalle = this.findViewById(R.id.spinnerCalle);
        this.spinnerZona = this.findViewById(R.id.spinnerZona);
        this.buttonCalle = this.findViewById(R.id.buttonCalle);

        this.temp=this.findViewById(R.id.textTemperatura);
        temp.setTextColor(Color.WHITE);
        this.lluvi=this.findViewById(R.id.textLloviendo);
        lluvi.setTextColor(Color.WHITE);
        this.humedo=this.findViewById(R.id.textHumedo);
        humedo.setTextColor(Color.WHITE);

        this.f0=this.findViewById(R.id.textFranja0);
        this.f1=this.findViewById(R.id.textFranja1);
        this.f2=this.findViewById(R.id.textFranja2);
        this.f3=this.findViewById(R.id.textFranja3);
        this.f4=this.findViewById(R.id.textFranja4);
        this.f5=this.findViewById(R.id.textFranja5);
        this.f6=this.findViewById(R.id.textFranja6);
        this.f7=this.findViewById(R.id.textFranja7);
        this.f8=this.findViewById(R.id.textFranja8);
        this.f9=this.findViewById(R.id.textFranja9);
        this.f10=this.findViewById(R.id.textFranja10);
        this.f11=this.findViewById(R.id.textFranja11);
        this.f12=this.findViewById(R.id.textFranja12);
        this.f13=this.findViewById(R.id.textFranja13);
        this.f14=this.findViewById(R.id.textFranja14);
        this.f15=this.findViewById(R.id.textFranja15);
        this.f16=this.findViewById(R.id.textFranja16);
        this.f17=this.findViewById(R.id.textFranja17);
        this.f18=this.findViewById(R.id.textFranja18);
        this.f19=this.findViewById(R.id.textFranja19);
        this.f20=this.findViewById(R.id.textFranja20);
        this.f21=this.findViewById(R.id.textFranja21);
        this.f22=this.findViewById(R.id.textFranja22);
        this.f23=this.findViewById(R.id.textFranja23);

        this.imagen=this.findViewById(R.id.imageView);


        //init the arraylist to incorpore the information
        this.listCalle = new ArrayList<>();
        this.listZona = new ArrayList<>();
        this.arrayCalle = new ArrayList<>();
        this.arrayZona = new ArrayList<>();
        this.arrayValores = new ArrayList<>();
        this.arrayHoras = new ArrayList<>();

        loadZonas();
        if(arrayZona.size()>0) {
           spinnerZona.setSelection(0);
        }

        //Add action when the spinner of the cities changes

        spinnerZona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idZona = listZona.get(i).getId();//Get the id of the selected position
                Log.i(tag, "Zona seleccionada:" + listZona.get(i).getNombre());

                //Get the list of stations of the selected city and set them into the spinner
                loadCalles(idZona);
                spinnerCalle.setAdapter(new ArrayAdapter<String>
                        (context, android.R.layout.simple_spinner_item, arrayCalle));
                //TODO

                if(arrayCalle.size()>0) {
                    spinnerCalle.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Add action when the spinner of the stations changes
        spinnerCalle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    idCalle = listCalle.get(i).getId();//Get the id of the selected position
                    nameCalle = listCalle.get(i).getNombre();
                    Log.i(tag, "Calle seleccionada:" + listCalle.get(i).getNombre());
                }catch (Exception e){
                    Log.e(tag, "Error escogiendo la calle:" + e.toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        buttonCalle.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(tag, "Boton presionado");

                f0.setTextColor(Color.GRAY);
                f1.setTextColor(Color.GRAY);
                f2.setTextColor(Color.GRAY);
                f3.setTextColor(Color.GRAY);
                f4.setTextColor(Color.GRAY);
                f5.setTextColor(Color.GRAY);
                f6.setTextColor(Color.GRAY);
                f7.setTextColor(Color.GRAY);
                f8.setTextColor(Color.GRAY);
                f9.setTextColor(Color.GRAY);
                f10.setTextColor(Color.GRAY);
                f11.setTextColor(Color.GRAY);
                f12.setTextColor(Color.GRAY);
                f13.setTextColor(Color.GRAY);
                f14.setTextColor(Color.GRAY);
                f15.setTextColor(Color.GRAY);
                f16.setTextColor(Color.GRAY);
                f17.setTextColor(Color.GRAY);
                f18.setTextColor(Color.GRAY);
                f19.setTextColor(Color.GRAY);
                f20.setTextColor(Color.GRAY);
                f21.setTextColor(Color.GRAY);
                f22.setTextColor(Color.GRAY);
                f23.setTextColor(Color.GRAY);

                cambiar(idCalle);

                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {}
                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception
                    {
                        String mqttText = new String(message.getPayload());
                        int tipo, dato;
                        tipo=Integer.parseInt(mqttText.split(";")[0]);
                        Log.e(tag, Integer.toString(tipo));
                        dato=Integer.parseInt(mqttText.split(";")[1]);
                        Log.e(tag, Integer.toString(dato));
                        switch (tipo){
                            case 0:
                                //Temperatura
                                temp.setText(dato+"ºC");
                                temp.setTextColor(Color.GRAY);
                                break;
                            case 1:
                                //Humedad
                                humedo.setText(dato+"%");
                                humedo.setTextColor(Color.GRAY);
                                break;
                            case 4:
                                //lluvia
                                if(dato==0){
                                    lluvi.setText("No está lloviendo");
                                }else{
                                    lluvi.setText("Esta lloviendo");
                                }
                                lluvi.setTextColor(Color.GRAY);
                                break;
                            case 2:
                                if (dato==0){
                                    imagen.setImageResource(R.drawable.noche);
                                }else{
                                    imagen.setImageResource(R.drawable.logo);
                                }
                            default:
                                break;
                        }
                    }
                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {}
                });

            }
        });
    }



    //Search the cities and fill the spinner with the information
    private void loadZonas(){
        String url = "http://192.168.151.14:8080/smartstreet/GetZonasCiudad?codigoCiudad=1";
        ServerConnectionThread thread = new ServerConnectionThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    //Search the stations of the selected city and fill the spinner with the information
    private void loadCalles(final int calleId){

        String url = "http://192.168.151.14:8080/smartstreet/GetCallesZona?codigoCiudad=1&idZona="+calleId;
        this.listCalle = new ArrayList<>();
        this.arrayCalle = new ArrayList<>();
        ServerConnectionThread thread = new ServerConnectionThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    //Select the Zonas from JSON response
    public void setListZonas(JSONArray jsonZonas){
        try {
            for (int i = 0; i < jsonZonas.length(); i++) {
                JSONObject jsonobject = jsonZonas.getJSONObject(i);
                listZona.add(new Zona(jsonobject.getInt("_id"),
                        jsonobject.getInt("_codigoCiudad"),
                        jsonobject.getString("_nombre")));
                arrayZona.add(jsonobject.getString("_nombre"));
            }
            spinnerZona.setAdapter(new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, arrayZona));
        }catch (Exception e){
            Log.e(tag,"Error: " + e);
        }
    }

    //Select the stations from JSON response
    public void setListCalles(JSONArray jsonZonas){
        Log.e(tag,"Cargando las calles de " + jsonZonas);
        try {
            for (int i = 0; i < jsonZonas.length(); i++) {
                JSONObject jsonobject = jsonZonas.getJSONObject(i);
                listCalle.add(new Calle(jsonobject.getInt("_idZona"),
                        jsonobject.getInt("_codigoCiudadZona"),
                        jsonobject.getString("_nombre")));
                arrayCalle.add(jsonobject.getString("_nombre"));
                Log.e(tag,"Calle " + jsonobject.getString("_nombre"));
            }
        }catch (Exception e){
            Log.e(tag,"Error: " + e);
        }
    }

    public void cargarValores(JSONArray jsonValores,String nombreCalle){
        Log.e(tag,"Cargando los valores " + jsonValores);
        String url = "http://192.168.151.14:8080/smartstreet/GetHorasPuntaCalle?codigoCiudad=1&idZona="+idZona+"&nombreCalle="+nombreCalle;
        ServerConnectionThread thread = new ServerConnectionThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
        ArrayList<Sensor> array =new ArrayList<>();
        double valor;
        String tipo;
        try {
            if (jsonValores!=null){
                for (int i = 0; i < jsonValores.length(); i++) {
                    JSONObject jsonobject = jsonValores.getJSONObject(i);
                    array.add(new Sensor(jsonobject.getInt("_codigoCiudadZonaCalle"),
                            jsonobject.getInt("_idZonaCalle"),
                            jsonobject.getDouble("_valor"),
                            jsonobject.getString("_nombreCalle"),
                            jsonobject.getString("_tipo"),
                            jsonobject.getString("_unidadMedida")));
                    Log.e(tag,array.get(i).getTipo());

                    tipo=jsonobject.getString("_tipo");
                    valor=jsonobject.getDouble("_valor");
                    if (tipo.equals("temperatura")){
                        temp.setText(String.valueOf(valor)+"ºC");
                    }else if(tipo.equals("humedad")){
                        humedo.setText(String.valueOf(valor)+"%");
                    }else if (tipo.equals("lluvia")){
                        if (valor==0){
                            lluvi.setText("No está lloviendo");
                        }else{
                            lluvi.setText("Está lloviendo");
                        }
                    }else if (tipo.equals("horarioConflictivo")){

                    }else if(tipo.equals("luz")){
                        if (valor==0){
                            imagen.setImageResource(R.drawable.noche);
                        }else{
                            imagen.setImageResource(R.drawable.logo);
                        }
                    }
                }
            }
        }catch (Exception e){
            Log.e(tag,"Error: " + e);
        }
    }
    public void cambiar(int idCalle){
        String url = "http://192.168.151.14:8080/smartstreet/GetSensoresCalle?codigoCiudad=1&idZona="+idZona+"&nombreCalle=calle"+idCalle;
        ServerConnectionThread thread = new ServerConnectionThread(this, url);

        try {
            thread.join();
        }catch (InterruptedException e){}
        temp.setText("No hay datos");
        lluvi.setText("No hay datos");
        humedo.setText("No hay datos");

        cargarValores(jsonValores,nameCalle);
        temp.setTextColor(Color.GRAY);
        lluvi.setTextColor(Color.GRAY);
        humedo.setTextColor(Color.GRAY);
    }

    public void setJsonValores(JSONArray jsonValores) {
        this.jsonValores=jsonValores;
    }

    //MQTT topics to suscribe the application
    private void suscripcionTopics(int ciudad,int zona, int calle){
        try{
            Log.i(tag, "ciudad = " + ciudad);
            Log.i(tag, "zona = " + zona);
            Log.i(tag, "calle = " + calle);

            client.subscribe("ciudad" + ciudad + "/zona" +zona+ "/datos/+",2);
            client.subscribe("ciudad" + ciudad + "/zona" +zona+ "/calles/calle"+calle+"/horarioConflictivo",2);

        }catch (MqttException e){
            e.printStackTrace();
        }
    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name = "Notificación";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    //Method to create a notfication with the title and the message
    private void createNotification(String title, String msn){
        //Configure the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle(title);
        builder.setContentText(msn);
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.BLUE, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        //Show the notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    public void setHoras(JSONArray jsonHoras){
        Log.e(tag,"Modificando horas punta");
        arrayHoras=new ArrayList<>();
        try {
            for (int i = 0; i < jsonHoras.length(); i++) {
                JSONObject jsonobject = jsonHoras.getJSONObject(i);
                arrayHoras.add(new HoraPunta(jsonobject.getInt("_idZonaCalle"),
                        jsonobject.getInt("_codigoCiudadCalle"),
                        jsonobject.getString("_nombreCalle"),
                        jsonobject.getString("_horaInicio"),
                        jsonobject.getString("_horaInicio"),
                        jsonobject.getBoolean("_fijo")));
            }
        }catch (Exception e){
            Log.e(tag,"Error: " + e);
        }
        for (HoraPunta hora : arrayHoras){
            Log.e(tag,hora.getHoraInicio());
            int horaInicio=Integer.parseInt(hora.getHoraInicio().split(":")[0]);
            Log.e(tag, String.valueOf(horaInicio));
            int horaFin=Integer.parseInt(hora.getHoraFin().split(":")[0]);
            Log.e(tag, String.valueOf(horaInicio));
            for (int i=horaInicio;i<horaFin+1;i++){
                Log.e(tag, String.valueOf(i));
                switch (i){
                    case 0:
                        f0.setTextColor(Color.RED);
                        break;
                    case 1:
                        f1.setTextColor(Color.RED);
                        break;
                    case 2:
                        f2.setTextColor(Color.RED);
                        break;
                    case 3:
                        f3.setTextColor(Color.RED);
                        break;
                    case 4:
                        f4.setTextColor(Color.RED);
                        break;
                    case 5:
                        f5.setTextColor(Color.RED);
                        break;
                    case 6:
                        f6.setTextColor(Color.RED);
                        break;
                    case 7:
                        f7.setTextColor(Color.RED);
                        break;
                    case 8:
                        f8.setTextColor(Color.RED);
                        break;
                    case 9:
                        f9.setTextColor(Color.RED);
                        break;
                    case 10:
                        f10.setTextColor(Color.RED);
                        break;
                    case 11:
                        f11.setTextColor(Color.RED);
                        break;
                    case 12:
                        f12.setTextColor(Color.RED);
                        break;
                    case 13:
                        f13.setTextColor(Color.RED);
                        break;
                    case 14:
                        f14.setTextColor(Color.RED);
                        break;
                    case 15:
                        f15.setTextColor(Color.RED);
                        break;
                    case 16:
                        f16.setTextColor(Color.RED);
                        break;
                    case 17:
                        f17.setTextColor(Color.RED);
                        break;
                    case 18:
                        f18.setTextColor(Color.RED);
                        break;
                    case 19:
                        f19.setTextColor(Color.RED);
                        break;
                    case 20:
                        f20.setTextColor(Color.RED);
                        break;
                    case 21:
                        f21.setTextColor(Color.RED);
                        break;
                    case 22:
                        f22.setTextColor(Color.RED);
                        break;
                    case 23:
                        f23.setTextColor(Color.RED);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
