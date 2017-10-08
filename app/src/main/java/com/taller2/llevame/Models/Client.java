package com.taller2.llevame.Models;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by MaríaAgustina on 23/9/2017.
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

    public boolean isDriver;

    public Client client(String dictionary){

        return this;
    }
}
