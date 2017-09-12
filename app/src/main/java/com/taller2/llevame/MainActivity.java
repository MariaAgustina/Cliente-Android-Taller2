package com.taller2.llevame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import com.taller2.llevame.serviceLayerModel.HTTPRequest;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log
        Log.v(TAG,"Hello world");
        //Log.w(TAG,"Probando un warning");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HTTPRequest request = new HTTPRequest();
        request.sendHTTPRequest();
    }

    public void goToLoginActivity(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
