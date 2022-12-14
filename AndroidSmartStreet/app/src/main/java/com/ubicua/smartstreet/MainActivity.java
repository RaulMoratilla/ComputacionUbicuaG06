package com.ubicua.smartstreet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //setContentView(R.layout.activity_select_calle);
                Intent i = new Intent(MainActivity.this, SelectCalleActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);

    }
}