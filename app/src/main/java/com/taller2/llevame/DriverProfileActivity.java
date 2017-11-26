package com.taller2.llevame;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
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

    private static final String TAG = "ProfileActivity";

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

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("NotificationData")
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String comunicationToken = intent.getExtras().getString("comunicationToken");
            String name = intent.getExtras().getString("name");
            String surname = intent.getExtras().getString("surname");
            String from = intent.getExtras().getString("address_from");
            String to = intent.getExtras().getString("address_to");

            String alertString = "Pasajero " + name + " " + surname + " desea viajar desde " + from + " hasta " + to;
            showAlertDialog(alertString);
        }
    };


    private void showAlertDialog(String alertString){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Nuevo Viaje")
                .setMessage(alertString)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * method is invoked to know that the client has sent successfully the location
     */
    public void onPostLocationSuccess() {

    }

}
