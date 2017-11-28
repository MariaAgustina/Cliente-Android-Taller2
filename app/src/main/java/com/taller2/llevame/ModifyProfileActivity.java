package com.taller2.llevame;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.taller2.llevame.Creational.FactoryActivities;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.Models.ClientData;
import com.taller2.llevame.Models.FacebookData;
import com.taller2.llevame.serviceLayerModel.ClientRequest;

import java.util.ArrayList;

public class ModifyProfileActivity extends BaseAtivity {

    private static final String TAG = "ModifyProfileActivity";
    private Client client;
    private TextView editNameInput;
    private TextView editSurnameInput;
    private TextView countryInput;
    private TextView emailInput;
    private TextView birthdateInput;

    /**
     * the creation of the modify profile activity
     * @param savedInstanceState the instance state of the bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);
        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
        this.setUpInitials();
        this.setUpClientInformation();
    }

    /**
     * set ups the views that contain the class
     */
    private void setUpInitials (){
        this.client = (Client) getIntent().getSerializableExtra("client");
        this.editNameInput = (TextView) findViewById(R.id.editNameInput);
        this.editSurnameInput = (TextView) findViewById(R.id.editSurnameInput);
        this.birthdateInput = (TextView) findViewById(R.id.birthdateInput);
        this.emailInput = (TextView) findViewById(R.id.emailInput);
        this.countryInput = (TextView) findViewById(R.id.countryInput);
    }

    /**
     * the client information received from the server is saved in the controller properties
     */
    private void setUpClientInformation(){
        this.editNameInput.setText(this.client.name);
        this.editSurnameInput.setText(this.client.surname);
        this.birthdateInput.setText(this.client.birthdate);
        this.emailInput.setText(this.client.email);
        this.countryInput.setText(this.client.country);
    }

    /**
     * this method handles the action when de button for changes is pressed
     * @param view the view that contains the button
     */
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

        ClientData clientData= new ClientData();

        clientData.username = client.username;
        clientData.password = client.password;

        clientData.firstName = name;
        this.client.name = name;
        clientData.lastName = surname;
        this.client.surname = surname;
        //TODO: arreglarlo trae problemas
        clientData.birthdate = "\"01/01/1990\"";
        this.client.birthdate = birthdate;
        clientData.email = email;
        this.client.email = email;
        clientData.country = country;
        this.client.country = country;
        clientData.images = new ArrayList();

        String type =  (client.type.equals("driver")) ? "driver" : "client";

        FacebookData facebookData = new FacebookData();
        facebookData.authToken = (this.client.fb_auth_token != null) ? this.client.fb_auth_token : "fb_token";
        facebookData.userId = (this.client.fb_user_id != null) ? this.client.fb_user_id : "fb_user_id";

        clientData.fb = facebookData;

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setClientEndPoint(type,client.id);
        clientRequest.modifyProfile(this,clientData);
    }

    /**
     * shows a toast that explains the user there is an error
     * @param error the error that comes from the server
     */
    public void onServiceDidFailed(VolleyError error) {
        super.onServiceDidFailed(error);
        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
    }


    /**
     * client success save new data and go back to profile activity
     */
    public void onModifyClientSuccess(){
        Toast.makeText(getApplicationContext(),R.string.modify_profile_success,Toast.LENGTH_SHORT).show();
        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
        FactoryActivities factoryActivities = new FactoryActivities();
        if(client.isDriver()){
            factoryActivities.goToDriverProfileActivity(this,this.client);
        }else{
            factoryActivities.goToPassengerProfileActivity(this,this.client);
        }
    }

}
