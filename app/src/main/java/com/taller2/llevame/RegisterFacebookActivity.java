package com.taller2.llevame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.taller2.llevame.Creational.FactoryActivities;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.Models.ClientData;
import com.taller2.llevame.Models.FacebookData;
import com.taller2.llevame.Views.LoadingView;
import com.taller2.llevame.serviceLayerModel.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class RegisterFacebookActivity extends BaseAtivity {

    private static final String TAG = "RegisterWithFacebookAct";

    private TextView countryInput;
    private Switch isADriverSwitch;
    private LoadingView loadingView;
    private LoginButton loginbutton;
    private CallbackManager callbackManager;
    ClientData clientData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_facebook);
        this.setUpInitials();
        this.setupCustomLoginComponents();
    }

    private void setUpInitials() {
        this.countryInput = (TextView) findViewById(R.id.countryInput);
        this.isADriverSwitch = (Switch) findViewById(R.id.switchIsDriver);
        this.loadingView = new LoadingView();
        this.loadingView.setLoadingViewInvisible(this);
        this.loginbutton = (LoginButton) findViewById(R.id.login_button);

    }

    protected void setupCustomLoginComponents(){
        this.callbackManager = CallbackManager.Factory.create();

        this.loginbutton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        this.loginbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        //Log.v(TAG, loginResult.getAccessToken().getToken());
                        //showProfileActivity();
                        // String facebookAccessToken = loginResult.getAccessToken().getToken();
                        //doLoginWithFacebook(facebookAccessToken);

                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v(TAG, response.toString());
                                try {
                                    String email = object.getString("email");
                                    String birthdate = object.getString("birthday"); // 01/31/1980 format
                                    String name = object.getString("first_name");
                                    String surname = object.getString("last_name");
                                    String fbId = object.getString("id");

                                    Log.v(TAG,object.toString());
                                    registerUserWithData(name,surname,fbId,email,birthdate);

                                } catch (JSONException e) {

                                }
                            }

                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,locale,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();

            }


            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),R.string.cancel_login,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),R.string.login_error,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


    public void registerWithFacebookButtonPressed(View view) {

        String country = this.countryInput.getText().toString();
        if(country.equals("")){
            Toast.makeText(getApplicationContext(),R.string.country_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        this.loadingView.setLoadingViewVisible(this);
        this.loginbutton.performClick();

    }

    private void registerUserWithData(String name,String surname,String fbId,String email,String birthdate){

        boolean isADriver = this.isADriverSwitch.isChecked();
        String type = (isADriver) ? "driver" :"client";
        String country = this.countryInput.getText().toString();

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEndPoint(type);

        ClientData client = new ClientData();
        client.username = fbId+"99999";//fbId;
        client.password = fbId;

        //esta bien que este hardcodeado
        FacebookData facebookData = new FacebookData();
        facebookData.authToken = fbId + "99999"; //TODO: le estoy agregando este numero para no registrar mi usuario posta
        facebookData.userId = fbId + "99999";

        client.fb = facebookData;
        client.firstName = name;
        client.lastName = surname;
        client.country = country;
        client.email = email;
        //TODO: agregarlo al formulario o ver de sacar los caracteres de escape. la fecha de cumpleaños me trajo mucho kilombo los requests daban mal.
        client.birthdate = "\"01/01/1990\"";//birthdate;
        client.images = new ArrayList();

        this.clientData = client;
        registerRequest.registerClient(this,client);
    }

    public void onRegisterClientSuccess() {
        Toast.makeText(getApplicationContext(),R.string.register_succeded,Toast.LENGTH_SHORT).show();
        this.loadingView.setLoadingViewInvisible(this);

        //esto esta asi porque no se respeto el modelo de respuesta
        Client client = new Client();
        client.username = this.clientData.username;
        client.name = this.clientData.firstName;
        String type = (this.isADriverSwitch.isChecked()) ? "driver" :"client";
        client.type = type;
        client.birthdate = this.clientData.birthdate;
        client.country = this.clientData.country;
        client.surname = this.clientData.lastName;

        FactoryActivities factoryActivities = new FactoryActivities();

        if(this.isADriverSwitch.isChecked()){
            factoryActivities.goToDriverProfileActivity(this,client);
        }else{
            factoryActivities.goToPassengerProfileActivity(this,client);
        }

        factoryActivities.goToProfileActivity(this,client);
    }

    public void onServiceDidFailed(VolleyError error){
        super.onServiceDidFailed(error);
        this.loadingView.setLoadingViewInvisible(this);

    }
}
