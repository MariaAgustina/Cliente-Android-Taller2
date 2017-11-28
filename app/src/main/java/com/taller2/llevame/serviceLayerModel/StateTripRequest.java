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
import com.taller2.llevame.Models.ClientData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by amarkosich on 11/28/17.
 */

public class StateTripRequest extends HTTPRequest{

    private static final String TAG = "StateTripRequest";

    /**
     *
     * @param type could be accept, start or end, it would determinate the endpoint url
     *
     */
    public StateTripRequest(String type,String clientId,String tripId){

        if(type.equals("accept")){
            this.endponintUrl = "/api/v1/driver/"+ clientId +"/trips/" + tripId + "/accept";
        }else if(type.equals("start")){
            this.endponintUrl = "/api/v1/client/" + clientId + "/trips/"+ tripId+"/start";
        }else if(type.equals("end")){
            this.endponintUrl = "/api/v1/client/" + clientId + "/trips/" + tripId +"/finish";
        }
        this.configureUrl();

    }

    /**
     *
     * @param delegate the delegate that will be notified when the async request has success or error
     */
    public void sendStateTripRequest(final BaseAtivity delegate){


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, this.url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG,"Response is: "+ response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                delegate.onServiceDidFailed(error);
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
    }

}
