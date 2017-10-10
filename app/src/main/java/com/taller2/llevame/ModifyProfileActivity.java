package com.taller2.llevame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.taller2.llevame.Creational.FactoryActivities;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.serviceLayerModel.ClientRequest;

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
        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
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
        this.editNameInput.setText(this.client.name);
        this.editSurnameInput.setText(this.client.surname);
        this.birthdateInput.setText(this.client.birthdate);
        this.emailInput.setText(this.client.email);
        this.countryInput.setText(this.client.country);
    }

    public void saveChangesButtonPressed(View view){

        String name = this.editNameInput.getText().toString();
        if(name.equals("")){
            Toast.makeText(getApplicationContext(),R.string.name_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        String surname = this.editSurnameInput.getText().toString();
        if(surname.equals("")){
            Toast.makeText(getApplicationContext(),R.string.surname_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        String country = this.countryInput.getText().toString();
        if(country.equals("")){
            Toast.makeText(getApplicationContext(),R.string.country_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        String email = this.emailInput.getText().toString();
        if(email.equals("")){
            Toast.makeText(getApplicationContext(),R.string.email_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        String birthdate = this.birthdateInput.getText().toString();
        if(birthdate.equals("")){
            Toast.makeText(getApplicationContext(),R.string.birthdate_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        this.client.name = name;
        this.client.surname = surname;
        this.client.birthdate = birthdate;
        this.client.email = email;
        this.client.country = country;

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setClientEndPoint(client.type,client.id);
        clientRequest.modifyProfile(this,client);
    }


    public void onServiceDidFailed(VolleyError error) {
        super.onServiceDidFailed(error);
        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
    }

    public void onModifyClientSuccess(){
        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
        //TODO
    }

}
