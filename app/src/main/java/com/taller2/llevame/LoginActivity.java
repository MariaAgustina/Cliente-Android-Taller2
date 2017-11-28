package com.taller2.llevame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.taller2.llevame.Views.LoadingView;
import com.taller2.llevame.serviceLayerModel.LoginFacebookRequest;
import com.taller2.llevame.serviceLayerModel.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * the login activity has the option to login with llevame or login with facebook
 */
public class LoginActivity extends BaseAtivity {

    private static final String TAG = "LoginActivity";
    public static final String SESSION_SETTINGS = "sessionSettings";

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView userNameInput;
    private TextView passwordInput;
    private LoadingView loadingView;

    /**
     *
     * @param savedInstanceState the instance state of the bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setUpInitials();
        this.setupCustomLoginComponents();
    }

    /**
     * setup and initialize views
     */
    private void setUpInitials (){
        this.userNameInput = (TextView) findViewById(R.id.userNameInput);
        this.passwordInput = (TextView) findViewById(R.id.passwordInput);
        this.loadingView = new LoadingView();
        this.loadingView.setLoadingViewInvisible(this);
    }

    /**
     * login with facebook set up
     */
    protected void setupCustomLoginComponents(){
        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),

                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v(TAG, response.toString());
                        try {
                            String fbId = object.getString("id") + "99999";
                            Log.v(TAG,fbId);
                            doLoginWithFacebook(fbId);

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

    /**
     * the login with faceebok result
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    /**
     * the login after logeed in with facebook was successful, it sends the login to llevame with the accessToken
     * @param accessToken
     */
    public void doLoginWithFacebook(String accessToken){
        this.loadingView.setLoadingViewVisible(this);
        LoginFacebookRequest loginFacebookRequest = new LoginFacebookRequest(accessToken);
        loginFacebookRequest.login(this);
    }

    /**
     * this method is invoked when the app login button is pressed
     * @param view the view that sends the message to the app
     */
    public void loginLlevameButtonPressed(View view){
        this.loadingView.setLoadingViewVisible(this);
        String userName = this.userNameInput.getText().toString();
        String password = this.passwordInput.getText().toString();
        LoginRequest loginRequest = new LoginRequest(userName,password);
        loginRequest.login(this);
    }


    /**
     * this method is invoked when the login with llevame is successful
     * @param client the client that returns the login request
     */
    public void onLoginSuccess(Client client){
        this.loadingView.setLoadingViewInvisible(this);
        FactoryActivities factoryActivities = new FactoryActivities();
        saveSessionData(client);
        if(client.isDriver()){
            factoryActivities.goToDriverProfileActivity(this,client);
        }else{
            factoryActivities.goToPassengerProfileActivity(this,client);
        }
    }

    private void saveSessionData(Client client) {

        SharedPreferences settings = getSharedPreferences(SESSION_SETTINGS, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean("sessionSaved",true);
        editor.putString("birthdate",client.birthdate);
        editor.putString("id",client.id);
        editor.putString("country",client.country);
        editor.putString("email",client.email);
        editor.putString("fb_user_id",client.fb_user_id);
        editor.putString("fb_auth_token",client.fb_auth_token);
        editor.putString("name",client.name);
        editor.putString("surname",client.surname);
        editor.putString("type",client.type);
        editor.putString("username",client.username);

        editor.commit();

    }
        /**
         * method when the login fail
         * @param error the error received from service
         */
    public void onServiceDidFailed(VolleyError error) {
        Log.e("error en la resupuesta", error.toString());
        this.loadingView.setLoadingViewInvisible(this);
        Toast.makeText(getApplicationContext(),R.string.login_error,Toast.LENGTH_SHORT).show();
    }


}
