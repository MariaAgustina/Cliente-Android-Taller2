package com.taller2.llevame.serviceLayerModel;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.BaseFragmentActivity;
import com.taller2.llevame.CookieHolder;
import com.taller2.llevame.Models.AvailableDriver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by amarkosich on 11/19/17.
 */

public class AvailableDriversRequest extends  HTTPRequest {

    private static final String TAG = "AvailableDriversRequest";

    public AvailableDriversRequest(LatLng latLng, double radio){
        this.endponintUrl = "/api/v1/closestdrivers/latitude/"+ latLng.latitude +"/length/" + latLng.longitude + "/radio/" +radio;
        this.configureUrl();
    }

    /**
     * request get list of available drivers
     * @param delegate the delegate that will be notified when the async request has success or error
     */
    public void getAvailableDrivers(final BaseAtivity delegate){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //por el problema de Long SS
                        response = response.replace("long","Long");
                        Log.v(TAG,"Response is: "+ response);

                        Gson gson = new Gson();

                        List<AvailableDriver> driversList = Arrays.asList(gson.fromJson(response,
                                AvailableDriver[].class));
                        delegate.onAvailableDriverSuccess(driversList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error en la resupuesta", error.toString());
                delegate.onServiceDidFailed(error);
            }
        })
        {
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

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
