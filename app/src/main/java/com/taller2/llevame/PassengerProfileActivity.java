package com.taller2.llevame;

import android.os.Bundle;
import android.view.View;

/**
 * Created by amarkosich on 10/8/17.
 */

public class PassengerProfileActivity extends ProfileActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUpInitials(){
        super.setUpInitials();
        this.findViewById(R.id.selectDriver).setVisibility(View.VISIBLE);
    }

    public void selectDriverButtonPressed(View view) {
        //TODO: en el checkpoint 3
    }

}
