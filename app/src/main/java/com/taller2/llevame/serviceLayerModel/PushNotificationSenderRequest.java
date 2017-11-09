package com.taller2.llevame.serviceLayerModel;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.CookieHolder;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.Models.PushNotification;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by amarkosich on 11/8/17.
 */

public class PushNotificationSenderRequest {
    private static final String TAG = "PushNotification";

    private String baseUrl;
    public String endponintUrl;
    public String url;

    /**
     PushNotificationSenderRequest()
     * The request constructor
     */
    public PushNotificationSenderRequest(){
        this.baseUrl = "https://fcm.googleapis.com/fcm/send";
        this.endponintUrl = "";
        this.url = "";
    }

    public void configureUrl(){
        this.url = baseUrl;
    }

    public void sendPushNotification(final BaseAtivity delegate,PushNotification pushNotification){

        this.configureUrl();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        Gson gson = new Gson();
        String json = gson.toJson(pushNotification);

        try {

            JSONObject jsonObject = new JSONObject(json);


            // Request a string response from the provided URL.
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, this.url, jsonObject,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v(TAG,"Response is: "+ response);

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    delegate.onServiceDidFailed(error);
                    Log.v(TAG,"Error: " +error.getMessage());
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "key=AIzaSyBKVJW1AAZFh1T2EHIrGbR52Y7Qff09LTM");

                    return headers;
                }
            };

            queue.add(stringRequest);

        }catch (JSONException e){
            Log.e(TAG,"error when converting json to JSONObject");
        }
    }
}
