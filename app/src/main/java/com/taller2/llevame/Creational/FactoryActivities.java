package com.taller2.llevame.Creational;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.LoginActivity;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.ModifyProfileActivity;

/**
 * Created by MaríaAgustina on 5/10/2017.
 */

public class FactoryActivities {

    private static final String TAG = "FactoryActivities";

    private void goToActivity(BaseAtivity activity,Class<?> cls){
        Intent intent = new Intent(activity,cls);
        activity.startActivity(intent);
    }

    public void goToProfileActivity(BaseAtivity activity, Client client){
        Log.v(TAG,"go to profile activity");
        ModifyProfileActivity  modifyProfileActivity= new ModifyProfileActivity();
        Intent intent = new Intent(activity,ModifyProfileActivity.class);
        intent.putExtra("client",client);
        activity.startActivity(intent);
    }

    public void goToLoginActivity(BaseAtivity activity){
        Log.v(TAG,"go to login activity");
        this.goToActivity(activity,LoginActivity.class);
    }
    public void goToRegisterActivity(){
        Log.v(TAG,"go to register activity");
    }
}
