package com.taller2.llevame.Models;

import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by MaríaAgustina on 23/9/2017.
 */

/**
 * The class that is received in the requests
 */

public class Client implements Serializable{

    public String birthdate;
    public String id;
    public String country;
    public String email;
   // public String fb_user_id;
   // public String fb_auth_token;
    public String name;
    public String surname;
    public String type;
    public String username;

    private static final String TAG = "Client";

    public boolean isDriver(){
        if(this.type !=null){
            return this.type.equals("driver");
        }
        Log.w(TAG,"type is null");
        return false;
    }

}
