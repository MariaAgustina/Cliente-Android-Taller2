package com.taller2.llevame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.taller2.llevame.Creational.FactoryActivities;

public class ModifyProfileActivity extends BaseAtivity {

    private static final String TAG = "ModifyProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);
    }

    public void saveChangesButtonPressed(View view){
        TextView editNameInput = (TextView) findViewById(R.id.editNameInput);
        String name = editNameInput.getText().toString();
        Log.v(TAG,name);
    }

}
