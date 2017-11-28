package com.taller2.llevame;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.taller2.llevame.Creational.FactoryActivities;
import com.taller2.llevame.Models.ClientData;
import com.taller2.llevame.Models.FacebookData;
import com.taller2.llevame.Views.LoadingView;
import com.taller2.llevame.serviceLayerModel.RegisterRequest;

import java.util.ArrayList;

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
    private LoadingView loadingView;
    private Switch isADriverSwitch;

    /**
     *
     * @param savedInstanceState the instance state of the bundle
     */
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
        this.isADriverSwitch = (Switch) findViewById(R.id.switchIsDriver);
        this.loadingView = new LoadingView();
        this.loadingView.setLoadingViewInvisible(this);
    }

    /**
     * method check the user had completed all changes
     * @param view
     */
    public void registerButtonPressed(View view) {

        String username = this.editUserNameInput.getText().toString();
        if(username.equals("")){
            Toast.makeText(getApplicationContext(),R.string.username_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        String password = this.editPasswordInput.getText().toString();
        if(password.equals("")){
            Toast.makeText(getApplicationContext(),R.string.password_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        String confirmPassword = this.confirmPasswordInput.getText().toString();
        if(confirmPassword.equals("")){
            Toast.makeText(getApplicationContext(),R.string.confirm_password_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        if(!confirmPassword.equals(password)){
            Toast.makeText(getApplicationContext(),R.string.passwords_not_match,Toast.LENGTH_SHORT).show();
            return;
        }

        String name = this.nameInput.getText().toString();
        if(name.equals("")){
            Toast.makeText(getApplicationContext(),R.string.name_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        String surname = this.surnameInput.getText().toString();
        if(surname.equals("")){
            Toast.makeText(getApplicationContext(),R.string.surname_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        String country = this.countryInput.getText().toString();
        if(country.equals("")){
            Toast.makeText(getApplicationContext(),R.string.country_should_not_empty,Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: validar email
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

        boolean isADriver = this.isADriverSwitch.isChecked();
        String type =  (isADriver) ? "driver" :"client";

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEndPoint(type);

        ClientData client = new ClientData();
        client.username = username;
        client.password = password;

        //esta bien que este hardcodeado
        FacebookData facebookData = new FacebookData();
        facebookData.authToken = "authToken";
        facebookData.userId = "userid";

        client.fb = facebookData;
        client.firstName = name;
        client.lastName = surname;
        client.country = country;
        client.email = email;
        client.birthdate = birthdate;
        client.images = new ArrayList();
        client.type = type;

        this.loadingView.setLoadingViewVisible(this);
        registerRequest.registerClient(this, client);
    }

    /**
     * shows a toast informing that the user has registered successfuly and then goes to login activity
     */
    public void onRegisterClientSuccess() {
        Toast.makeText(getApplicationContext(),R.string.register_succeded,Toast.LENGTH_SHORT).show();
        this.loadingView.setLoadingViewInvisible(this);

        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToLoginActivity(this);
    }

    /**
     *
     * @param error
     */
    public void onServiceDidFailed(VolleyError error){
        super.onServiceDidFailed(error);
        this.loadingView.setLoadingViewInvisible(this);

    }
}
