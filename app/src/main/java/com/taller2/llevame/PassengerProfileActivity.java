package com.taller2.llevame;

import android.os.Bundle;
import android.view.View;

/**
 * Created by amarkosich on 10/8/17.
 */

public class PassengerProfileActivity extends ProfileActivity {

    /**
     *
     * @param savedInstanceState the instance state of the bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * set up initial view values
     */
    @Override
    public void setUpInitials(){
        super.setUpInitials();
        this.findViewById(R.id.selectDriver).setVisibility(View.VISIBLE);
    }

    /**
     * will show the list of drivers available for the passenger
     * @param view the view that contains the button
     */
    public void selectDriverButtonPressed(View view) {
        //TODO: en el checkpoint 3
    }

}
