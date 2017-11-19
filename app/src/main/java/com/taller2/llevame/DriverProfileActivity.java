package com.taller2.llevame;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.taller2.llevame.serviceLayerModel.LastLocationRequest;

/**
 * Created by amarkosich on 10/8/17.
 */

public class DriverProfileActivity extends ProfileActivity {


    /**
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
    public void setUpInitials() {
        super.setUpInitials();
        this.findViewById(R.id.switchAvailable).setVisibility(View.VISIBLE);
        this.findViewById(R.id.switchOnTrip).setVisibility(View.VISIBLE);
        postLocation();
    }

    private void postLocation(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null)
            {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                LastLocationRequest lastLoactionRequest = new LastLocationRequest(location,this.client.id);
                lastLoactionRequest.postLastLocation(this);
            }

        }else {
            Toast.makeText(getApplicationContext(),R.string.permisson_denied_error,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * method is invoked to know that the client has sent successfully the location
     */
    public void onPostLocationSuccess() {

    }

}
