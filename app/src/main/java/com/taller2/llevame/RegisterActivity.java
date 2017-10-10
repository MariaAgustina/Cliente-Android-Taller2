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

import com.taller2.llevame.Models.Client;
import com.taller2.llevame.serviceLayerModel.ClientRequest;

public class RegisterActivity extends BaseAtivity {

    private static final String TAG = "RegisterActivity";
    private TextView editUserNameInput;
    private TextView editPasswordInput;
    private TextView confirmPasswordInput;
    private TextView nameInput;
    private TextView surnameInput;
    private TextView countryInput;
    private TextView emailInput;
    private TextView birthdateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setUpInitials();
    }

    private void setUpInitials() {
        this.editUserNameInput = (TextView) findViewById(R.id.editUserNameInput);
        this.editPasswordInput = (TextView) findViewById(R.id.editPasswordInput);
        this.confirmPasswordInput = (TextView) findViewById(R.id.confirmPasswordInput);
        this.nameInput = (TextView) findViewById(R.id.nameInput);
        this.surnameInput = (TextView) findViewById(R.id.surnameInput);
        this.birthdateInput = (TextView) findViewById(R.id.birthdateInput);
        this.emailInput = (TextView) findViewById(R.id.emailInput);
        this.countryInput = (TextView) findViewById(R.id.countryInput);
    }

    public void registerButtonPressed(View view) {

        /*String username = this.editUserNameInput.getText().toString();
        if(username.equals("")){
            Toast.makeText(getApplicationContext(),R.string.username_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        String surname = this.editPasswordInput.getText().toString();
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
        }*/

/*        String username = this.editUserNameInput.getText().toString();
        String password = this.editPasswordInput.getText().toString();

        this.client.name = name;
        this.client.surname = surname;
        this.client.birthdate = birthdate;
        this.client.email = email;
        this.client.country = country;

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        Log.v(TAG,client.surname);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setClientEndPoint(client.type,client.id);
        clientRequest.modifyProfile(this,client);*/
    }


}
