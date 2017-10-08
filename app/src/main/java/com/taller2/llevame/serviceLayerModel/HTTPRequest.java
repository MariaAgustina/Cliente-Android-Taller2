package com.taller2.llevame.serviceLayerModel;
import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import static com.facebook.FacebookSdk.getApplicationContext;
import com.android.volley.DefaultRetryPolicy;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Created by MaríaAgustina on 11/9/2017.
 */

public class HTTPRequest {

    private static final String TAG = "HTTPRequest";

    private  String baseUrl;
    public String endponintUrl;
    public String url;

    public HTTPRequest (){
        this.baseUrl = "http://taller2-application-server.herokuapp.com"; //App server
        //this.baseUrl = "https://stormy-lowlands-30400.herokuapp.com/api/v1"; //Shared server
        this.endponintUrl = "";
        this.url = "";
    }

    public void configureUrl(){
        this.url = baseUrl+endponintUrl;
    }

    public void sendHTTPRequest(){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG,"Response is: "+ response);
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

    public void getTokenSharedServer(){
        String requestUrl = "https://stormy-lowlands-30400.herokuapp.com/api/v1/llevame";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG,"Response is: "+ response);

                        JsonElement dataElem = new JsonParser().parse(response);
                        JsonElement je = dataElem.getAsJsonObject().get("token");
                        String token = je.toString();
                        Log.v(TAG,token);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error en la resupuesta", error.toString());
            }
        });
        queue.add(stringRequest);

    }

}
