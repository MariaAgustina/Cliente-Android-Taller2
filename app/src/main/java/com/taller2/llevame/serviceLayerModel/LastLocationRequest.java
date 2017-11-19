package com.taller2.llevame.serviceLayerModel;

import android.location.Location;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.BaseFragmentActivity;
import com.taller2.llevame.CookieHolder;
import com.taller2.llevame.Models.LLELocation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by amarkosich on 11/19/17.
 */

public class LastLocationRequest extends  HTTPRequest {
    private static final String TAG = "LastLocationRequest";
    private Location location;
    private String userId;

    public LastLocationRequest(Location location, String userId){
        this.location = location;
        this.userId = userId;
    }

    /**
     * request get list of available drivers
     * @param delegate the delegate that will be notified when the async request has success or error
     */
    public void getLastLocation(final BaseAtivity delegate){
        //TODO
    }
    /**
     * request post last location of available drivers
     * @param delegate the delegate that will be notified when the async request has success or error
     */
    public void postLastLocation(final BaseAtivity delegate) {
        this.endponintUrl = "/api/v1/lastlocation";
        this.configureUrl();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        Gson gson = new Gson();

        LLELocation llevameLocation = new LLELocation();
        llevameLocation.user_id = this.userId;
        llevameLocation.accuracy = this.location.getAccuracy();
        llevameLocation.lat = this.location.getLatitude();
        llevameLocation.Long = this.location.getLongitude();

        String json = gson.toJson(llevameLocation).toLowerCase();

        try {
            JSONObject jsonObject = new JSONObject(json);
            Log.v(TAG,jsonObject.toString());

            // Request a string response from the provided URL.
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, this.url, jsonObject,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v(TAG,"Response is: "+ response);

                            delegate.onPostLocationSuccess();
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
                    return headers;
                }
            };


            queue.add(stringRequest);
        }catch (JSONException e){
            Log.e(TAG,"error when converting json to JSONObject");
        }

    }



}
