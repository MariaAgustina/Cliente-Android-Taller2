package com.taller2.llevame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.taller2.llevame.Creational.FactoryActivities;
import com.taller2.llevame.Models.Client;

public class SplashActivity extends BaseAtivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private static final String TAG = "SplashActivity";
    public static final String SESSION_SETTINGS = "sessionSettings";

    @Override
    protected void onCreate(Bundle icicle) {
        Log.v(TAG,"calling splash");
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = getIntentToShow();
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private Intent getIntentToShow(){

        SharedPreferences settings = getSharedPreferences(SESSION_SETTINGS, 0);
        boolean isLoggedIn = settings.getBoolean("sessionSaved",false);


        if(isLoggedIn){
            Client client = getSession();
            FactoryActivities factoryActivities = new FactoryActivities();
            if(client.isDriver()){
                Intent driverIntent = new Intent(this,DriverProfileActivity.class);
                driverIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                driverIntent.putExtra("client",client);
                return driverIntent;
            }else{
                Intent clientIntent = new Intent(this,PassengerProfileActivity.class);
                clientIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                clientIntent.putExtra("client",client);
                return clientIntent;
            }
        }

        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private Client getSession(){
        SharedPreferences settings = getSharedPreferences(SESSION_SETTINGS, 0);

        Client client = new Client();

        client.birthdate = settings.getString("birthdate","");
        client.id = settings.getString("id","");
        client.country = settings.getString("country","");
        client.email = settings.getString("email","");
        client.fb_user_id = settings.getString("fb_user_id","");
        client.fb_auth_token = settings.getString("fb_auth_token","");
        client.name =settings.getString("name","");
        client.surname = settings.getString("surname","");
        client.type = settings.getString("type","");
        client.username = settings.getString("username","");
        String cookie = settings.getString("cookie","");
        CookieHolder.INSTANCE.setCookie(cookie);

        return client;
    }

}

