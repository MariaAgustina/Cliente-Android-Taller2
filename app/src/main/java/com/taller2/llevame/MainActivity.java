package com.taller2.llevame;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import com.taller2.llevame.Creational.FactoryActivities;
import com.taller2.llevame.serviceLayerModel.HTTPRequest;

public class MainActivity extends BaseAtivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToLoginActivity(View view){
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToLoginActivity(this);
    }


    public void goToRegisterButtonPressed(View view) {
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToRegisterActivity(this);
    }
}
