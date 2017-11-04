package com.taller2.llevame;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.login.LoginManager;
import com.taller2.llevame.Creational.FactoryActivities;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.Models.ClientData;
import com.taller2.llevame.Views.LoadingView;
import com.taller2.llevame.serviceLayerModel.ClientRequest;

public class ProfileActivity extends BaseAtivity {

    private static final String TAG = "ProfileActivity";
    private Client client;
    private LoadingView loadingView;
    private TextView profileTitle;

    /**
     *
     * @param savedInstanceState the instance state of the bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.client = (Client) getIntent().getSerializableExtra("client");
        this.setUpInitials();
        //ClientRequest clientRequest = new ClientRequest();
        //clientRequest.setClientEndPoint(this.session.client_id,this.session.type_client);
        //clientRequest.getClient(this);
    }

    /**
     * set up initial view values
     */
    public void setUpInitials(){
        this.loadingView = new LoadingView();
        this.loadingView.setLoadingViewInvisible(this);
        this.profileTitle = (TextView) findViewById(R.id.profile_title);
        if(this.client != null){
            this.profileTitle.setText("Hola " + client.name);
        }
    }

    /**
     * logout from facebook
     * @param view
     */
    public void logout(View view){
        //cierro sesion en facebook
        LoginManager.getInstance().logOut();
        goToMainActivity();
    }

    /**
     * modify profil button pressed
     * @param view the view that contains the go to modifyProfile button
     */
    public void modifyProfile(View view){
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToModifyProfileActivity(this,this.client);
    }

    /**
     * delete profile button pressed
     * @param view the view that contains the go to delete button
     */
    public void deleteProfile(View view){
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToDeleteProfileActivity(this,this.client);
    }

    /**
     * this method shows the main activity
     */
    private void goToMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent,1);
    }

    /**
     *
     * @param client the client that received from the server
     */
    @Override
    public void onGetClientSuccess(Client client){
        this.loadingView.setLoadingViewInvisible(this);
        this.client = client;
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    /**
     * method will show toast saying to the user that there was an error
     * @param error
     */
    public void onServiceDidFailed(VolleyError error){
        super.onServiceDidFailed(error);
        this.loadingView.setLoadingViewInvisible(this);
    }
}
