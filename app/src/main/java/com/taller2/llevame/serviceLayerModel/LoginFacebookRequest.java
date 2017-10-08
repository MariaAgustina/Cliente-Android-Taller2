package com.taller2.llevame.serviceLayerModel;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.Models.Client;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by amarkosich on 10/8/17.
 */

public class LoginFacebookRequest extends  HTTPRequest {

    private static final String TAG = "LoginFacebookRequest";

    public LoginFacebookRequest(String accessToken){
        this.endponintUrl = "/login/facebookAuthToken/" + accessToken;
        this.configureUrl();
    }

    public void login(final BaseAtivity delegate){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG,"Response is: "+ response);
                        //TODO: cuando ande
                        //JsonElement dataElem = new JsonParser().parse(response);
                        //JsonElement je = dataElem.getAsJsonObject().get("user");

                        //Gson gson = new Gson();
                        //Client client = gson.fromJson(je,Client.class);
                        //delegate.onLoginSuccess(client);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error en la resupuesta", error.toString());
                delegate.onServiceDidFailed(error);
            }
        });

        queue.add(stringRequest);
    }

}
