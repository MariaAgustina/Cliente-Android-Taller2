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
import com.taller2.llevame.ChatActivity;
import com.taller2.llevame.DeleteProfileActivity;
import com.taller2.llevame.DriverProfileActivity;
import com.taller2.llevame.LoginActivity;
import com.taller2.llevame.MainActivity;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.Models.Session;
import com.taller2.llevame.ModifyProfileActivity;
import com.taller2.llevame.PassengerProfileActivity;
import com.taller2.llevame.ProfileActivity;
import com.taller2.llevame.RegisterActivity;
import com.taller2.llevame.RegisterFacebookActivity;

/**
 * Created by MaríaAgustina on 5/10/2017.
 */

/**
 * Factory to show activities, it is used for a better navigation mantainance
 */
public class FactoryActivities {

    private static final String TAG = "FactoryActivities";

    /**
     * general method to show activities
     * @param activity the activity that needs to show other activity in the stack
     * @param cls the class of the activity that will be shown
     */
    private void goToActivity(BaseAtivity activity,Class<?> cls){
        Intent intent = new Intent(activity,cls);
        activity.startActivity(intent);
    }

    /**
     * modify profile
     * @param activity the activity that needs to show other activity in the stack
     * @param client the client object need to send in the extras of intents
     */
    public void goToModifyProfileActivity(BaseAtivity activity, Client client){
        Log.v(TAG,"go to profile activity");
        ModifyProfileActivity  modifyProfileActivity= new ModifyProfileActivity();
        Intent intent = new Intent(activity,ModifyProfileActivity.class);
        intent.putExtra("client",client);
        activity.startActivity(intent);
    }


    public void goToDeleteProfileActivity(BaseAtivity activity, Client client){
        Log.v(TAG,"go to delete profile activity");
        DeleteProfileActivity deleteProfileActivity = new DeleteProfileActivity();
        Intent intent = new Intent(activity,DeleteProfileActivity.class);
        //ModifyProfileActivity  modifyProfileActivity= new ModifyProfileActivity();
        //Intent intent = new Intent(activity,ModifyProfileActivity.class);
        intent.putExtra("client",client);
        activity.startActivity(intent);
    }


    /**
     * will go to login activity
     * @param activity the activity that needs to show other activity in the stack
     */

    public void goToLoginActivity(BaseAtivity activity){
        Log.v(TAG,"go to login activity");
        this.goToActivity(activity,LoginActivity.class);
    }

    /**
     * will go to profile activity
     * @param activity the activity that needs to show other activity in the stack
     * @param client the client object need to send in the extras of intents
     */
    public void goToProfileActivity(BaseAtivity activity,Client client){
        Log.v(TAG,"go to profile activity");
        Intent intent = new Intent(activity,ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("client",client);
        activity.startActivity(intent);
    }

    /**
     * will go to driver profile activity
     * @param activity the activity that needs to show other activity in the stack
     * @param client the client object need to send in the extras of intents
     */
    public void goToDriverProfileActivity(BaseAtivity activity,Client client){
        Log.v(TAG,"go to driver profile activity");
        Intent intent = new Intent(activity,DriverProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("client",client);
        activity.startActivity(intent);
    }

    /**
     * will go to passenger profile activity
     * @param activity the activity that needs to show other activity in the stack
     * @param client the client object need to send in the extras of intents
     */
    public void goToPassengerProfileActivity(BaseAtivity activity,Client client){
        Log.v(TAG,"go to passenger profile activity");
        Intent intent = new Intent(activity,PassengerProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("client",client);
        activity.startActivity(intent);
    }

    /**
     * will go to register activity
     * @param activity the activity that needs to show other activity in the stack
     */
    public void goToRegisterActivity(BaseAtivity activity){
        Log.v(TAG,"go to register activity");
        goToActivity(activity, RegisterActivity.class);
    }

    /**
     * will go to main activity
     * @param activity the activity that needs to show other activity in the stack
     */
    public void goToMainActivity(BaseAtivity activity){
        Intent intent = new Intent(activity,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    /**
     * will go to register activity
     * @param activity the activity that needs to show other activity in the stack
     */
    public void goToRegisterWithFacebookActivity(BaseAtivity activity){
        Log.v(TAG,"go to register activity");
        goToActivity(activity, RegisterFacebookActivity.class);
    }

    /**
     * will go to chat activity
     * @param activity the activity that needs to show other activity in the stack
     */
    public void goToChatActivity(BaseAtivity activity,String senderUserName,String receiverUserName){
        Log.v(TAG,"go to register activity");
        Intent intent = new Intent(activity,ChatActivity.class);
        intent.putExtra("senderUserName",senderUserName);
        intent.putExtra("receiverUserName",receiverUserName);
        activity.startActivity(intent);
    }

}
