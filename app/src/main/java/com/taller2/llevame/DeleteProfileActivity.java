package com.taller2.llevame;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.taller2.llevame.Creational.FactoryActivities;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.Models.ClientData;
import com.taller2.llevame.serviceLayerModel.ClientRequest;

public class DeleteProfileActivity extends BaseAtivity {
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setUpInitials();
    }

    private void setUpInitials() {
        this.client = (Client) getIntent().getSerializableExtra("client");
    }

    public void deleteProfileButtonPressed(View view) {
        String type =  (client.type.equals("driver")) ? "driver" : "client";

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setClientEndPoint(type,client.id);

        ClientData clientData = new ClientData();
        clientRequest.deleteProfile(this, clientData);
    }

    public void onServiceDidFailed(VolleyError error){
        //TODO: esto esta asi porque supuestamente se elimina bien el usuario en el server pero retorna un error.
        if(isLoggedInFacebook()){
            LoginManager.getInstance().logOut();
        }
        Toast.makeText(getApplicationContext(),R.string.delete_success,Toast.LENGTH_SHORT).show();
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToMainActivity(this);
    }

    public boolean isLoggedInFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

}
