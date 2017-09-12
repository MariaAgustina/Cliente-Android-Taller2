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


/**
 * Created by MaríaAgustina on 11/9/2017.
 */

public class HTTPRequest {

    private static final String TAG = "HTTPRequest";

    public void sendHTTPRequest(){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://www.google.com";
   //     String url ="http://taller2-application-server.herokuapp.com";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        Log.v(TAG,"Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("error en la resupuesta", error.toString());
                            //mTextView.setText("That didn't work!");
                         }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

}
