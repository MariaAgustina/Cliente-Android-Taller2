package com.taller2.llevame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.serviceLayerModel.LoginRequest;

public class LoginActivity extends BaseAtivity {

    private static final String TAG = "LoginActivity";

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView userNameInput;
    private TextView passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setUpInitials();
        this.setupCustomLoginComponents();
    }

    private void setUpInitials (){
        this.userNameInput = (TextView) findViewById(R.id.userNameInput);
        this.passwordInput = (TextView) findViewById(R.id.passwordInput);
    }

    protected void setupCustomLoginComponents(){
        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showProfileActivity();
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

    public void loginLlevameButtonPressed(View view){
        String userName = this.userNameInput.getText().toString();
        String password = this.passwordInput.getText().toString();
        LoginRequest loginRequest = new LoginRequest(userName,password);
        loginRequest.login(this);
    }

    public void onLoginSuccess(){

    }

    //TODO: ponerlo en el factory
    private void showProfileActivity(){
        Intent intent = new Intent(this,ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
