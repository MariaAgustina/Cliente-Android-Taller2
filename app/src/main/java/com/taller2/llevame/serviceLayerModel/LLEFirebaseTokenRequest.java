package com.taller2.llevame.serviceLayerModel;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.taller2.llevame.BaseAtivity;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by amarkosich on 11/26/17.
 */

public class LLEFirebaseTokenRequest {
    private static final String TAG = "ChatRequest";

    private String baseUrl;
    public String endponintUrl;
    public String url;

    /**
     LLEFirebaseTokenRequest()
     * Firebase Token Request gets token for specific client
     */
    public LLEFirebaseTokenRequest(){
        this.baseUrl = "https://cliente-android-taller2.firebaseio.com/";
        this.endponintUrl = "firebaseTokens.json";
        this.url = "";
        this.configureUrl();

    }

    /**
     * set the endpoint for the specific request
     */

    public void configureUrl(){
        this.url = baseUrl+endponintUrl;
    }

    /**
     * send the request for corresponding service
     */
    public void getFirebaseToken(final BaseAtivity delegate,final String clientId){


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG,"Response is: "+ response);
                        JsonElement dataElem = new JsonParser().parse(response);
                        JsonElement je = dataElem.getAsJsonObject().get(clientId);
                        Log.v(TAG,"Comunication Token is: "+ je.getAsString());
                        delegate.onGetFirebaseComunicationTokenSuccess(je.getAsString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error en la resupuesta", error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}