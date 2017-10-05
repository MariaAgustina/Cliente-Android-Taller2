package com.taller2.llevame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.login.LoginManager;
import com.taller2.llevame.serviceLayerModel.ClientRequest;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.getClient();
    }

    public void logout(View view){

        //cierro sesion en facebook
        LoginManager.getInstance().logOut();
        goToMainActivity();
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
