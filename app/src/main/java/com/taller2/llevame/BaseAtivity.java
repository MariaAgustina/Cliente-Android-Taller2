package com.taller2.llevame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.taller2.llevame.Models.Client;

public class BaseAtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_ativity);
    }

    public void onGetClientSuccess(Client client){

    }
}
