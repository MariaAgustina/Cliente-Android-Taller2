package com.taller2.llevame.Models;

/**
 * Created by amarkosich on 10/10/17.
 */

import java.util.ArrayList;

/**
 * The class that will be sent to the requests
 */

public class ClientData {
    public String username = "";
    public String password = "";
    public FacebookData fb = new FacebookData();
    public String lastName = "";
    public String firstName = "";
    public String country = "";
    public String birthdate = "";
    public String email = "";
    public String type = "";
    public ArrayList images = new ArrayList();
}
