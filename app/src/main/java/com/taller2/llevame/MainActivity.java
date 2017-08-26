package com.taller2.llevame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.Object;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log
        Log.v(TAG,"Hello world");
        Log.w(TAG,"Probando un warning");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
