package com.taller2.llevame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.taller2.llevame.Models.Chat;
import com.taller2.llevame.Models.Client;

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
        //TODO: si hay tiempo, modificarlo para que sea un popup
        Log.e("error en la resupuesta", error.toString());
        Toast.makeText(getApplicationContext(),R.string.server_failed,Toast.LENGTH_SHORT).show();
    }

    public void onLoginWithFacebookServiceDidFail(VolleyError error) {
        Log.e("error en la resupuesta", error.toString());
        Toast.makeText(getApplicationContext(),R.string.server_login_facebook_failed,Toast.LENGTH_SHORT).show();

    }
        //Esto esta asi para que los servicios se puedan llamar desde cualquier Activity

    //to override
    public void onGetClientSuccess(Client client){

    }

    //to override
    public void onLoginSuccess(Client client){

    }
    //to override
    public void onModifyClientSuccess(){

    }

    //to override
    public void onRegisterClientSuccess() {

    }

    //to override
    public void onGetChatSuccess(Chat chat) {

    }

    public void onDeleteProfileSuccess() {

    }
}
