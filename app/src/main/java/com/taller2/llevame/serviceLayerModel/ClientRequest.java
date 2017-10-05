package com.taller2.llevame.serviceLayerModel;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.Models.Client;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by MaríaAgustina on 23/9/2017.
 */

public class ClientRequest extends  HTTPRequest {

    private static final String TAG = "ClientRequest";

    public ClientRequest(){
        this.endponintUrl = "/api/v1/driver/1";
        this.configureUrl();
    }

    public void getClient(final BaseAtivity delegate){

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
            }
        });


        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
