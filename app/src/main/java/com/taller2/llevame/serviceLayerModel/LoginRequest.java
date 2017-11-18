package com.taller2.llevame.serviceLayerModel;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.CookieHolder;
import com.taller2.llevame.Models.Client;

import java.util.regex.Pattern;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by MaríaAgustina on 7/10/2017.
 */

public class LoginRequest extends  HTTPRequest {

    private static final String TAG = "LoginRequest";
    private String username;
    private String password;

    /**
     * Constructor
     * @param usernamae the username that the user inputs
     * @param passwrod  the password tha the user inputs
     */
    public LoginRequest(String usernamae,String passwrod){
        this.endponintUrl = "/login/username/"+ usernamae +"/password/" + passwrod;
        this.username = usernamae;
        this.password = passwrod;
        this.configureUrl();
    }

    /**
     * the login request that is sent
     * @param delegate the delegate that will be notified when the async request has success or error
     */
    public void login(final BaseAtivity delegate){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.v(TAG,"Response is: "+ response);

                        JsonElement dataElem = new JsonParser().parse(response);
                        JsonElement je = dataElem.getAsJsonObject().get("user");

                        Gson gson = new Gson();
                        Client client = gson.fromJson(je,Client.class);
                        client.username = username;
                        client.password = password;
                        delegate.onLoginSuccess(client);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error en la resupuesta", error.toString());
                delegate.onServiceDidFailed(error);
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String rawCookie = response.headers.get("Set-Cookie");
                Log.v(TAG,"HEADERSSS: " + response.headers);
                Log.v(TAG,"response.headers.get(\"Set-Cookie\"):" + response.headers.get("Set-Cookie"));
                String parsedCookie = rawCookie.split(Pattern.quote(";"))[0];
                Log.v(TAG,"parsedCookie:" + parsedCookie);
                CookieHolder.INSTANCE.setCookie(parsedCookie);
                return super.parseNetworkResponse(response);
            }
        };



        queue.add(stringRequest);
    }
}
