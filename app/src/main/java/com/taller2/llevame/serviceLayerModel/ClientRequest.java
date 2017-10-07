package com.taller2.llevame.serviceLayerModel;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.Models.Client;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by MaríaAgustina on 23/9/2017.
 */

public class ClientRequest extends  HTTPRequest {

    private static final String TAG = "ClientRequest";

    private String registerEnpoint;
    private String getClientEndpoint;

    public ClientRequest(){
        this.registerEnpoint = "/api/v1/driver";
    }

    public void setClientEndPoint(String clientId,String clientType){
        this.getClientEndpoint = "/api/v1/" + clientType + "/" + clientId;
    }

    public void getClient(final BaseAtivity delegate){

        this.endponintUrl = this.getClientEndpoint;
        this.configureUrl();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG,"Response is: "+ response);
                        Gson gson = new Gson();
                        Client client = gson.fromJson(response,Client.class);
                        delegate.onGetClientSuccess(client);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error en la resupuesta", error.toString());
                delegate.onServiceDidFailed(error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    //TODO: arreglar este put porque cuando le apunto al server no hace nada, confirmar que este implementado del lado del server
    public void modifyProfile(final BaseAtivity delegate,Client newClient){

        this.endponintUrl = this.getClientEndpoint;
        this.configureUrl();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        Gson gson = new Gson();
        String json = gson.toJson(newClient);

        try {
            JSONObject jsonObject = new JSONObject(json);

        // Request a string response from the provided URL.
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, this.url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG,"Response is: "+ response);

                        delegate.onModifyClientSuccess();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                delegate.onServiceDidFailed(error);
            }
        });

            queue.add(stringRequest);
        }catch (JSONException e){
            Log.e(TAG,"error when converting json to JSONObject");
        }
        // Add the request to the RequestQueue.
    }

}
