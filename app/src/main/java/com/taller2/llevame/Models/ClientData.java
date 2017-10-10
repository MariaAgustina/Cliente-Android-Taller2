package com.taller2.llevame.Models;

/**
 * Created by amarkosich on 10/10/17.
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * The class that will be send in the requests
 */

public class ClientData {
    public String username;
    public String password;
    public FacebookData fb;
    public String lastName;
    public String firstName;
    public String country;
    public String birthdate;
    public String email;
    public ArrayList images;
}
