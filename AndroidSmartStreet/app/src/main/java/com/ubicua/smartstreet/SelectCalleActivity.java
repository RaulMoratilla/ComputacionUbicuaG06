package com.ubicua.smartstreet;

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
    private Button buttonRecargar;
    private TextView temp;
    private TextView lluvi;
    private TextView f1;
    private TextView f2;
    private TextView f3;
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
        this.f1=this.findViewById(R.id.textFranja1);
        this.f2=this.findViewById(R.id.textFranja2);
        this.f3=this.findViewById(R.id.textFranja3);

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
                int id = listZona.get(i).getId();//Get the id of the selected position
                Log.i(tag, "Zona seleccionada:" + listZona.get(i).getNombre());

                //Get the list of stations of the selected city and set them into the spinner
                loadCalles(listZona.get(i).getId());
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
}
