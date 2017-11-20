package com.taller2.llevame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.taller2.llevame.Creational.FactoryActivities;
import com.taller2.llevame.Models.GeoSearchResult;
import com.taller2.llevame.Views.DelayAutoCompleteTextView;
import com.taller2.llevame.Views.GeoAutoCompleteAdapter;
import com.taller2.llevame.serviceLayerModel.AvailableDriversRequest;

public class MapsActivity extends BaseFragmentActivity implements OnMapReadyCallback {


    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private View whereToGoButton;
    private View whereToGoView;

    private Integer THRESHOLD = 2;
    private DelayAutoCompleteTextView geo_autocomplete;
    private ImageView geo_autocomplete_clear;
    /**
     * creation of main activity
     * @param savedInstanceState the instance state of the bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setupInitials();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * set up the initial componnents of the activyty
     */
    private void setupInitials(){
        this.whereToGoButton = this.findViewById(R.id.whereToGoButton);
        this.whereToGoView = this.findViewById(R.id.whereToGoView);
        setUpGeoAutocompleteView();

    }

    /**
     * Set ups GeoAutocompleteView
     */
    private void setUpGeoAutocompleteView(){
        geo_autocomplete_clear = (ImageView) findViewById(R.id.geo_autocomplete_clear);

        geo_autocomplete = (DelayAutoCompleteTextView) findViewById(R.id.geo_autocomplete);
        geo_autocomplete.setThreshold(THRESHOLD);
        geo_autocomplete.setAdapter(new GeoAutoCompleteAdapter(this)); // 'this' is Activity instance

        geo_autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                GeoSearchResult result = (GeoSearchResult) adapterView.getItemAtPosition(position);
                geo_autocomplete.setText(result.getAddress());
            }
        });

        geo_autocomplete.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                {
                    geo_autocomplete_clear.setVisibility(View.VISIBLE);
                }
                else
                {
                    geo_autocomplete_clear.setVisibility(View.GONE);
                }
            }
        });

        geo_autocomplete_clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                geo_autocomplete.setText("");
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null)
            {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(14)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                getAvailableDrivers(latLng);
            }

        }else {
            Toast.makeText(getApplicationContext(),R.string.permisson_denied_error,Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void getAvailableDrivers(LatLng latLng){
        double radio = 100.0;
        AvailableDriversRequest availableDriversRequest = new AvailableDriversRequest(latLng,radio);
        availableDriversRequest.getAvailableDrivers(this);
    }

    /**
     * this method is called when gets succeded on available drivers list
     */
    public void onAvailableDriverSuccess() {

    }

    /**
     * onServiceDidFailed shows Toast to inform the user the server has failed
     * @param error to log in console
     */
    public void onServiceDidFailed(VolleyError error){
        Log.e("error en la resupuesta", error.toString());
        Toast.makeText(getApplicationContext(),R.string.server_failed,Toast.LENGTH_SHORT).show();
    }

    /**
     * Method is called when the where go button is pressed, it shows the activity that selects the trip
     * @param view the button view
     */
    public void whereToGoButtonPressed(View view){

        this.whereToGoButton.setVisibility(View.INVISIBLE);
        this.whereToGoView.setVisibility(View.VISIBLE);


    }

}
