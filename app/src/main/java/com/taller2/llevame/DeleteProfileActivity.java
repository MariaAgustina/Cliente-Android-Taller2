package com.taller2.llevame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

}
