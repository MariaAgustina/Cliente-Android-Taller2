package com.taller2.llevame;

import android.os.Bundle;
import android.view.View;

/**
 * Created by amarkosich on 10/8/17.
 */

public class DriverProfileActivity extends ProfileActivity{


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * the initials of the profile activity would find the corresponding views
     */
    @Override
    public void setUpInitials(){
        super.setUpInitials();
        this.findViewById(R.id.switchAvailable).setVisibility(View.VISIBLE);
        this.findViewById(R.id.switchOnTrip).setVisibility(View.VISIBLE);

    }
}
