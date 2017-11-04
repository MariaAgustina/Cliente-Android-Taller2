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

    /**
     * creation of main activity
     * @param savedInstanceState the instance state of the bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * this method will present the login activity. It handles when the login button is pressed.
     * @param view the view that contains the button
     */
    public void goToLoginActivity(View view){
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToLoginActivity(this);
    }

    /**
     * this method will present the register activity. It handles when the login button is pressed.
     * @param view the view that contains the button
     */
    public void goToRegisterButtonPressed(View view) {
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToRegisterActivity(this);
    }

    /**
     * this method will present the register with facebook activity. It handles when the login button is pressed.
     * @param view the view that contains the button
     */
    public void goToRegisterWithFacebookButtonPressed(View view) {
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToRegisterWithFacebookActivity(this);
    }


}
