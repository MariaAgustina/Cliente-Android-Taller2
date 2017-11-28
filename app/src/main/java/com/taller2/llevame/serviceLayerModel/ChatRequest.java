package com.taller2.llevame.serviceLayerModel;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.Models.Chat;

import java.util.LinkedHashMap;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by amarkosich on 11/7/17.
 */

public class ChatRequest {

    private static final String TAG = "ChatRequest";

    private String baseUrl;
    public String endponintUrl;
    public String url;

    /**
     ChatRequest()
     * The parent request constructor
     */
    public ChatRequest(){
        this.baseUrl = "https://cliente-android-taller2.firebaseio.com/";
        this.endponintUrl = "";
        this.url = "";
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

    public void getChat(final BaseAtivity delegate){

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
                        LinkedHashMap chatsDictionary = gson.fromJson(response,LinkedHashMap.class);
                        Chat chat = new Chat(chatsDictionary);
                        delegate.onGetChatSuccess(chat);
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
