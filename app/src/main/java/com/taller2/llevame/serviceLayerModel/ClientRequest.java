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
import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.CookieHolder;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.Models.ClientData;

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

    /**
     * The ClientRequest constructor
     */

    public ClientRequest(){
        this.registerEnpoint = "/api/v1/driver";
    }

    /**
     *
     * @param clientType driver or client
     * @param clientId the clientId that comes from the server
     */

    public void setClientEndPoint(String clientType,String clientId){
        this.getClientEndpoint = "/api/v1/" + clientType + "/" + clientId;
    }

    /**
     * request get client and send it to the delegate
     * @param delegate the delegate that will be notified when the async request has success or error
     */
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

    /**
     *
     * @param delegate the delegate that will be notified when the async request has success or error
     * @param newClient the client who's profile will be modified
     */
    public void modifyProfile(final BaseAtivity delegate,ClientData newClient){

        this.endponintUrl = this.getClientEndpoint;
        this.configureUrl();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        Gson gson = new Gson();
        String json = gson.toJson(newClient);

        try {
            JSONObject jsonObject = new JSONObject(json);
            Log.v(TAG,jsonObject.toString());

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
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    //headers.put("Set-Cookie","session=eyJ1c2VybmFtZSI6Im1maWRhbGdvIn0.DMBzlw.yghaCJ_TBtJru7lll0_Ol_Pwj5I");
                    //headers.put("Cookie","session=eyJ1c2VybmFtZSI6Im1maWRhbGdvIn0.DMBzlw.yghaCJ_TBtJru7lll0_Ol_Pwj5I");
                    headers.put("Set-Cookie", CookieHolder.INSTANCE.getCookie());
                    headers.put("Cookie",CookieHolder.INSTANCE.getCookie());
                    return headers;
                }
            };

            queue.add(stringRequest);
        }catch (JSONException e){
            Log.e(TAG,"error when converting json to JSONObject");
        }
        // Add the request to the RequestQueue.
    }

    /**
     *
     * @param delegate the delegate that will be notified when the async request has success or error
     * @param newClient the client that will be removed from the server
     */

    public void deleteProfile(final BaseAtivity delegate,ClientData newClient){
        Log.v(TAG,"EJECUTANDO DELETE");

        this.endponintUrl = this.getClientEndpoint;
        this.configureUrl();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.DELETE, this.url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG,"Response is: "+ response);
                        delegate.onDeleteProfileSuccess();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG,error.toString());
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
        // Add the request to the RequestQueue.
    }


}
