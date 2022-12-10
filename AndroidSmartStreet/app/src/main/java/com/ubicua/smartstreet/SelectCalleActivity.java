package com.ubicua.smartstreet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.ubicua.smartstreet.Data.Calle;
import com.ubicua.smartstreet.Data.Zona;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectCalleActivity extends AppCompatActivity {

    private String tag = "SelectCalle";

    private Spinner spinnerCalle;
    private Spinner spinnerZona;
    private Button buttonCalle;
    ArrayList<String> arrayCalle;
    private ArrayList<Calle> listCalle;
    ArrayList<String> arrayZona;
    private ArrayList<Zona> listZona;
    private final Context context;
    private int idCalle = 0;
    private String nameCalle = "";

    public SelectCalleActivity() {
        super();
        this.context = this;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init the spinners and the button
        this.spinnerCalle = this.findViewById(R.id.spinnerCalle);
        this.spinnerZona = this.findViewById(R.id.spinnerZona);
        this.buttonCalle = this.findViewById(R.id.buttonCalle);

        //init the arraylist to incorpore the information
        this.listCalle = new ArrayList<>();
        this.listZona = new ArrayList<>();
        this.arrayCalle = new ArrayList<>();
        this.arrayZona = new ArrayList<>();

        //Add action when the spinner of the cities changes
        spinnerZona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int id = listZona.get(i).getId();//Get the id of the selected position
                Log.i(tag, "City selected:" + listZona.get(i).getNombre());

                //Get the list of stations of the selected city and set them into the spinner
                loadStations(listZona.get(i).getId());
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
                    Log.i(tag, "Station selected:" + listCalle.get(i).getNombre());
                }catch (Exception e){
                    Log.e(tag, "Error on selecting Station:" + e.toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        buttonCalle.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(tag, "Button pressed");
                Intent i = new Intent(SelectCalleActivity.this, CalleActivity.class);
                i.putExtra("stationId", "station" + idCalle);
                i.putExtra("stationName", nameCalle);
                startActivity(i);
                finish();
            }
        });
        //Initial load of cities and stations
        loadCities();
        if(arrayZona.size()>0) {
            spinnerZona.setSelection(0);
        }
    }

    //Search the cities and fill the spinner with the information
    private void loadCities(){
        String url = "http://192.168.1.131:8080/UbicompServerExample/GetCities";
        ServerConnectionThread thread = new ServerConnectionThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    //Search the stations of the selected city and fill the spinner with the information
    private void loadStations(final int calleId){

        String url = "http://192.168.1.131:8080/UbicompServerExample/GetStationsCity?cityId="+calleId;
        this.listCalle = new ArrayList<>();
        this.arrayCalle = new ArrayList<>();
        ServerConnectionThread thread = new ServerConnectionThread(this, url);
        try {
            thread.join();
        }catch (InterruptedException e){}
    }

    //Select the Cities from JSON response
    public void setListCities(JSONArray jsonCities){
        try {
            for (int i = 0; i < jsonCities.length(); i++) {
                JSONObject jsonobject = jsonCities.getJSONObject(i);
                listZona.add(new Zona(jsonobject.getInt("id"),
                        jsonobject.getInt("codigoCiudad"),
                        jsonobject.getString("name")));
                arrayZona.add(jsonobject.getString("name"));
            }
            spinnerZona.setAdapter(new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, arrayZona));
        }catch (Exception e){
            Log.e(tag,"Error: " + e);
        }
    }

    //Select the stations from JSON response
    public void setListStations(JSONArray jsonCities){
        Log.e(tag,"Loading stations " + jsonCities);
        try {
            for (int i = 0; i < jsonCities.length(); i++) {
                JSONObject jsonobject = jsonCities.getJSONObject(i);
                listCalle.add(new Calle(jsonobject.getInt("id"),
                        jsonobject.getInt("codigoCiudadZona"),
                        jsonobject.getString("name")));
                arrayCalle.add(jsonobject.getString("name"));
                Log.e(tag,"Station " + jsonobject.getString("name"));
            }
        }catch (Exception e){
            Log.e(tag,"Error: " + e);
        }
    }
}
