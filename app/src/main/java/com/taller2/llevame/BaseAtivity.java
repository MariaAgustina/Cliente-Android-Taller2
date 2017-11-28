package com.taller2.llevame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.JsonElement;
import com.taller2.llevame.Models.AvailableDriver;
import com.taller2.llevame.Models.Chat;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.Models.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * The parent activity for all classes
 */
public class BaseAtivity extends AppCompatActivity {

    /**
     *on create for the base activity
     * @param savedInstanceState the instance state of the bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_ativity);
    }

    /**
     * onServiceDidFailed
     * @param error
     */
    public void onServiceDidFailed(VolleyError error){
        Log.e("error en la resupuesta", error.toString());
        Toast.makeText(getApplicationContext(),R.string.server_failed,Toast.LENGTH_SHORT).show();
    }

    public void onLoginWithFacebookServiceDidFail(VolleyError error) {
        Log.e("error en la resupuesta", error.toString());
        Toast.makeText(getApplicationContext(),R.string.server_login_facebook_failed,Toast.LENGTH_SHORT).show();

    }
    //Esto esta asi para que los servicios se puedan llamar desde cualquier Activity

    /**
     * to override
     */
    public void onGetClientSuccess(Client client){

    }

    /**
     * to override
     */
    public void onLoginSuccess(Client client){

    }

    /**
     * to override
     */
    public void onModifyClientSuccess(){

    }

    /**
     * to override
     */
    public void onRegisterClientSuccess() {

    }

    /**
     * to override
     */
    public void onGetChatSuccess(Chat chat) {

    }

    /**
     * to override
     */
    public void onDeleteProfileSuccess() {

    }

    /**
     * to override
     */
    public void onPostLocationSuccess() {

    }

    /**
     * to override
     */
    public void onAvailableDriverSuccess(List<AvailableDriver> drivers) {

    }

    /**
     * to override
     */
    public void onGetWaySuccess(ArrayList<Step> steps) {

    }

    /**
     * to override
     */
    public void onGetFirebaseComunicationTokenSuccess(String comunicationToken) {

    }

    /**
     * to override
     */
    public void onTripRequestSuccess(String tripId) {

    }
}
