package com.taller2.llevame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.facebook.login.LoginManager;
import com.taller2.llevame.Creational.FactoryActivities;

/**
 * Created by amarkosich on 10/8/17.
 */

public class PassengerProfileActivity extends ProfileActivity {

    private static final String PAYMENT_SETTINGS = "paymentSettings";

    /**
     *
     * @param savedInstanceState the instance state of the bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * set up initial view values
     */
    @Override
    public void setUpInitials(){
        super.setUpInitials();
        this.findViewById(R.id.selectDriver).setVisibility(View.VISIBLE);
        setPaymentMethodIfShould();
    }


    public void editPaymentMethodButtonPressed(View view){

        showPayMethodActivity();
    }


    private void setPaymentMethodIfShould(){
        SharedPreferences settings = getSharedPreferences(PAYMENT_SETTINGS, 0);
        String paymethodSaved = settings.getString("paymethod","");

        boolean paymethodAlreadySaved = !paymethodSaved.trim().isEmpty();
        if(paymethodAlreadySaved){
            return;
        }
        showPayMethodActivity();
    }

    private void showPayMethodActivity(){
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToPaymentActivity(this);
    }

    /**
     * will show the list of drivers available for the passenger
     * @param view the view that contains the button
     */
    public void startTripButtonPressed(View view) {
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToMapActivity(this,this.client);
    }

}
