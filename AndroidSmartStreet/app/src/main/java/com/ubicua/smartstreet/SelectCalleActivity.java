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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ubicua.smartstreet.Data.Calle;
import com.ubicua.smartstreet.Data.Zona;

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
    private TextView f1;
    private TextView f2;
    private TextView f3;
    ArrayList<String> arrayCalle;
    private ArrayList<Calle> listCalle;
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


    public SelectCalleActivity() {
        super();
        this.context = this;
    }

    protected void onCreate(Bundle savedInstanceState) {

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://172.20.10.10:1883", clientId);

        arrayZona.add("zona1");
        arrayCalle.add("calle1");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_calle);

        //Init the spinners and the buttons and textViews
        this.spinnerCalle = this.findViewById(R.id.spinnerCalle);
        this.spinnerZona = this.findViewById(R.id.spinnerZona);
        this.buttonCalle = this.findViewById(R.id.buttonCalle);
        this.buttonRecargar = this.findViewById(R.id.buttonRecargar);
        this.temp=this.findViewById(R.id.textTemperatura);
        temp.setTextColor(Color.WHITE);
        this.lluvi=this.findViewById(R.id.textLloviendo);
        lluvi.setTextColor(Color.WHITE);
        this.humedo=this.findViewById(R.id.textLloviendo);
        humedo.setTextColor(Color.WHITE);
        this.f1=this.findViewById(R.id.textFranja1);
        f1.setTextColor(Color.WHITE);
        this.f2=this.findViewById(R.id.textFranja2);
        f2.setTextColor(Color.WHITE);
        this.f3=this.findViewById(R.id.textFranja3);
        f3.setTextColor(Color.WHITE);

        //init the arraylist to incorpore the information
        this.listCalle = new ArrayList<>();
        this.listZona = new ArrayList<>();
        this.arrayCalle = new ArrayList<>();
        this.arrayZona = new ArrayList<>();

        //Initial load of cities and stations
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

                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {}
                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception
                    {
                        String mqttText = new String(message.getPayload());
                        int tipo, dato;
                        tipo=Integer.parseInt(mqttText.split("|")[0]);
                        dato=Integer.parseInt(mqttText.split("|")[1]);
                        switch (tipo){
                            case 0:
                                //Temperatura
                                temp.setText(dato+"ºC");
                                temp.setTextColor(Color.BLACK);
                                break;
                            case 1:
                                //Humedad
                                humedo.setText(dato+"%");
                                humedo.setTextColor(Color.BLACK);
                                break;
                            case 4:
                                //lluvia
                                if(dato==0){
                                    lluvi.setText("Esta despejado");
                                }else{
                                    lluvi.setText("Esta lloviendo");
                                }
                                lluvi.setTextColor(Color.BLACK);
                                break;
                            case 6:
                                //Horario Conflictivo
                                //TODO hacer lo del horario conflictivo bien
                                break;
                        }
                    }
                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {}
                });
                cambiarTextos();
            }
        });
    }



    //Search the cities and fill the spinner with the information
    private void loadZonas(){
        String url = "http://172.20.10.10:8080/smartstreet/GetZonasCiudad?codigoCiudad=ciudad1";
        ServerConnectionThread thread = new ServerConnectionThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    //Search the stations of the selected city and fill the spinner with the information
    private void loadCalles(final int calleId){

        String url = "http://172.20.10.10:8080/smartstreet/GetCallesZona?idZona="+calleId;
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
                listZona.add(new Zona(jsonobject.getInt("id"),
                        jsonobject.getInt("codigoCiudad"),
                        jsonobject.getString("nombre")));
                arrayZona.add(jsonobject.getString("nombre"));
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
                listCalle.add(new Calle(jsonobject.getInt("idZona"),
                        jsonobject.getInt("codigoCiudadZona"),
                        jsonobject.getString("nombre")));
                arrayCalle.add(jsonobject.getString("nombre"));
                Log.e(tag,"Calle " + jsonobject.getString("nombre"));
            }
        }catch (Exception e){
            Log.e(tag,"Error: " + e);
        }
    }

    public void cambiarTextos(){
        f1.setText("7:30-8:30");
        f1.setTextColor(Color.RED);
        f2.setText("14:00-15:00");
        f2.setTextColor(Color.RED);
        f3.setText("19:00-20:00");
        f3.setTextColor(Color.GREEN);
    }

    //MQTT topics to suscribe the application
    private void suscripcionTopics(int ciudad,int zona, int calle){
        try{
            Log.i(tag, "ciudad = " + ciudad);
            Log.i(tag, "zona = " + zona);
            Log.i(tag, "calle = " + calle);

            client.subscribe("ciudad" + ciudad + "/zona" +zona+ "/datos/+",0);
            client.subscribe("ciudad" + ciudad + "/zona" +zona+ "/calles/calle"+calle+"/horarioConflictivo",0);

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

}
