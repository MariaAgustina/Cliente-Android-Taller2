package com.taller2.llevame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.login.LoginManager;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.taller2.llevame.Creational.FactoryActivities;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.Views.LoadingView;

public class ProfileActivity extends BaseAtivity {

    private static final String TAG = "ProfileActivity";
    public Client client;
    private LoadingView loadingView;
    private TextView profileTitle;
    private FloatingActionButton fab;

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
    }

    /**
     * set up initial view values
     */
    public void setUpInitials(){
        this.loadingView = new LoadingView();
        this.loadingView.setLoadingViewInvisible(this);
        this.profileTitle = (TextView) findViewById(R.id.profile_title);
        this.fab = (FloatingActionButton)findViewById(R.id.fab);
        configFabButtonPressed();

        if(this.client != null){
            this.profileTitle.setText("Hola " + client.name);
        }

        saveFirebaseTokenForPushNotifiactions();
    }


    /**
     * Saves the token of the device in firebase database
     */
    private void saveFirebaseTokenForPushNotifiactions(){
        FirebaseDatabase.getInstance().getReference().child("firebaseTokens").child(client.id).setValue(FirebaseInstanceId.getInstance().getToken());
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

    public void configFabButtonPressed() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChat();
            }
        });
    }

    public void showChat(){
        //TODO: Eliminar valor hardcodeado
        Log.v(TAG,"go to chat");
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToChatActivity(this,client.username,"Oscar");
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
