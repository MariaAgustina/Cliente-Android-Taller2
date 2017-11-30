package com.taller2.llevame.serviceLayerModel;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.CookieHolder;
import com.taller2.llevame.Models.Trip;
import com.taller2.llevame.Models.TripData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by amarkosich on 11/30/17.
 */

public class EstimateTripRequest extends HTTPRequest {
    private static final String TAG = "EstimateTripRequest";

    public EstimateTripRequest(){
        this.endponintUrl = "/api/v1/trips/estimate";
        this.configureUrl();
    }

    /**
     * the estimate request
     * @param delegate the delegate that will be notified when the async request has success or error
     * @param trip the trip data
     */
    public void estimateTrip(final BaseAtivity delegate, Trip trip){


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        Gson gson = new Gson();
        String json = gson.toJson(trip);

        try {
            JSONObject jsonObject = new JSONObject(json);
            Log.v(TAG,jsonObject.toString());


            // Request a string response from the provided URL.
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, this.url, jsonObject,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v(TAG,"Response is: "+ response);
                                delegate.onEstimateTripRequestSuccess();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    delegate.onEstimateTripRequestError();
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
                    headers.put("Set-Cookie", CookieHolder.INSTANCE.getCookie());
                    headers.put("Cookie",CookieHolder.INSTANCE.getCookie());
                    return headers;
                }
            };


            queue.add(stringRequest);
        }catch (JSONException e){
            Log.e(TAG,"error when converting json to JSONObject");
        }


    }

}
