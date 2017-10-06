package com.taller2.llevame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.taller2.llevame.Creational.FactoryActivities;
import com.taller2.llevame.Models.Client;

public class ModifyProfileActivity extends BaseAtivity {

    private static final String TAG = "ModifyProfileActivity";
    private Client client;
    private TextView editNameInput;
    private TextView editSurnameInput;
    private TextView countryInput;
    private TextView emailInput;
    private TextView birthdateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);
        this.setUpInitials();
        this.setUpClientInformation();
    }

    private void setUpInitials (){
        this.client = (Client) getIntent().getSerializableExtra("client");
        this.editNameInput = (TextView) findViewById(R.id.editNameInput);
        this.editSurnameInput = (TextView) findViewById(R.id.editSurnameInput);
        this.birthdateInput = (TextView) findViewById(R.id.birthdateInput);
        this.emailInput = (TextView) findViewById(R.id.emailInput);
        this.countryInput = (TextView) findViewById(R.id.countryInput);
    }

    private void setUpClientInformation(){
        this.editNameInput.setText(this.client.first_name);
        this.editSurnameInput.setText(this.client.last_name);
        this.birthdateInput.setText(this.client.birthdate);
        this.emailInput.setText(this.client.email);
        this.countryInput.setText(this.client.country);
    }

    public void saveChangesButtonPressed(View view){
        String name = this.editNameInput.getText().toString();
        Log.v(TAG,name);
    }

}
