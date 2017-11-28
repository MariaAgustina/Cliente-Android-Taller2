package com.taller2.llevame;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.taller2.llevame.Creational.FactoryActivities;

public class MainActivity extends BaseAtivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_LOCATION = 0;


    /**
     * creation of main activity
     * @param savedInstanceState the instance state of the bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showLocationPermissionIfShould();
    }

    /**
     * Checks if app already has location permissions
     */
    private void showLocationPermissionIfShould() {
        // Check if the Location permission has been granted
        boolean hasLocationPermission = (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);

        if (!hasLocationPermission) {
            requestLocationPermission();
        }
    }

    /**
     * Requests the {@link android.Manifest.permission#ACCESS_FINE_LOCATION} permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    private void requestLocationPermission() {

        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            // Request the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_LOCATION);

        } else {
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_LOCATION);
        }
    }

    /**
     * this method will present the login activity. It handles when the login button is pressed.
     * @param view the view that contains the button
     */
    public void goToLoginActivity(View view){
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToLoginActivity(this);
    }

    /**
     * this method will present the register activity. It handles when the login button is pressed.
     * @param view the view that contains the button
     */
    public void goToRegisterButtonPressed(View view) {
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToRegisterActivity(this);
    }

    /**
     * this method will present the register with facebook activity. It handles when the login button is pressed.
     * @param view the view that contains the button
     */
    public void goToRegisterWithFacebookButtonPressed(View view) {
        FactoryActivities factoryActivities = new FactoryActivities();
        factoryActivities.goToRegisterWithFacebookActivity(this);
    }


}
