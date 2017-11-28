package com.taller2.llevame;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.taller2.llevame.Models.AvailableDriver;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.Models.Driver;
import com.taller2.llevame.Models.GeoSearchResult;
import com.taller2.llevame.Models.LLEAddress;
import com.taller2.llevame.Models.LLELocation;
import com.taller2.llevame.Models.Notification;
import com.taller2.llevame.Models.Paymethod;
import com.taller2.llevame.Models.PaymethodCashParameters;
import com.taller2.llevame.Models.PushNotification;
import com.taller2.llevame.Models.StartEndPointTrip;
import com.taller2.llevame.Models.Step;
import com.taller2.llevame.Models.Trajectory;
import com.taller2.llevame.Models.Trip;
import com.taller2.llevame.Models.TripData;
import com.taller2.llevame.Models.TripLocation;
import com.taller2.llevame.Models.TripRequestData;
import com.taller2.llevame.Views.DelayAutoCompleteTextView;
import com.taller2.llevame.Views.GeoAutoCompleteAdapter;
import com.taller2.llevame.serviceLayerModel.AvailableDriversRequest;
import com.taller2.llevame.serviceLayerModel.LLEFirebaseTokenRequest;
import com.taller2.llevame.serviceLayerModel.PushNotificationSenderRequest;
import com.taller2.llevame.serviceLayerModel.StateTripRequest;
import com.taller2.llevame.serviceLayerModel.TrajectoryRequest;
import com.taller2.llevame.serviceLayerModel.TripRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends BaseAtivity implements OnMapReadyCallback {


    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private View whereToGoButton;
    private View whereToGoView;

    private Integer THRESHOLD = 2;
    private DelayAutoCompleteTextView geo_autocomplete;
    private ImageView geo_autocomplete_clear;
    private DelayAutoCompleteTextView geo_autocomplete_to;
    private ImageView geo_autocomplete_clear_to;

    private ArrayList<Step> tripSteps;

    private Address addressFrom;
    private Address addressTo;
    private View loadingView;
    private List<AvailableDriver> availableDrivers;
    private Client client;
    private Driver selectedDriver;
    private StartEndPointTrip startPoint;
    private StartEndPointTrip endPoint;

    /**
     * creation of main activity
     *
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
    private void setupInitials() {
        this.whereToGoButton = this.findViewById(R.id.whereToGoButton);
        this.whereToGoView = this.findViewById(R.id.whereToGoView);
        setUpGeoAutocompleteView();
        setUpGeoAutocompleteToView();
        this.loadingView = this.findViewById(R.id.loadingPanel);
        this.client = (Client) getIntent().getSerializableExtra("client");

    }

    private void setLoadingViewInvisible(){
        this.loadingView.setVisibility(View.INVISIBLE);
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void setLoadingViewVisible(){
        this.loadingView.setVisibility(View.VISIBLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * Set ups GeoAutocompleteView
     */
    private void setUpGeoAutocompleteView() {
        geo_autocomplete_clear = (ImageView) findViewById(R.id.geo_autocomplete_clear);
        geo_autocomplete = (DelayAutoCompleteTextView) findViewById(R.id.geo_autocomplete);

        geo_autocomplete.setThreshold(THRESHOLD);
        geo_autocomplete.setAdapter(new GeoAutoCompleteAdapter(this)); // 'this' is Activity instance

        geo_autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                GeoSearchResult result = (GeoSearchResult) adapterView.getItemAtPosition(position);
                geo_autocomplete.setText(result.getAddress());
                addressFrom = result.getAddressObject();
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
                if (s.length() > 0) {
                    geo_autocomplete_clear.setVisibility(View.VISIBLE);
                } else {
                    geo_autocomplete_clear.setVisibility(View.GONE);
                }
            }
        });

        geo_autocomplete_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geo_autocomplete.setText("");
            }
        });

    }

    /**
     * Set ups GeoAutocompleteView
     */
    private void setUpGeoAutocompleteToView() {
        geo_autocomplete_clear_to = (ImageView) findViewById(R.id.geo_autocomplete_clear_to);
        geo_autocomplete_to = (DelayAutoCompleteTextView) findViewById(R.id.geo_autocomplete_to);

        geo_autocomplete_to.setThreshold(THRESHOLD);
        geo_autocomplete_to.setAdapter(new GeoAutoCompleteAdapter(this)); // 'this' is Activity instance

        geo_autocomplete_to.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                GeoSearchResult result = (GeoSearchResult) adapterView.getItemAtPosition(position);
                geo_autocomplete_to.setText(result.getAddress());
                addressTo = result.getAddressObject();

            }
        });

        geo_autocomplete_to.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    geo_autocomplete_clear_to.setVisibility(View.VISIBLE);
                } else {
                    geo_autocomplete_clear_to.setVisibility(View.GONE);
                }
            }
        });

        geo_autocomplete_clear_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geo_autocomplete_to.setText("");
            }
        });

    }

    private Location getLastKnownLocation() {
        Location bestLocation = null;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationManager mLocationManager;
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            List<String> providers = mLocationManager.getProviders(true);
            for (String provider : providers) {
                Location l = mLocationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            }
        }
        return bestLocation;
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

            Location location = this.getLastKnownLocation();
            if (location != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(14)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                getAvailableDrivers(latLng);
            } else {
                Toast.makeText(getApplicationContext(), R.string.no_location_error, Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), R.string.permisson_denied_error, Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void getAvailableDrivers(LatLng latLng) {
        double radio = 100.0;
        AvailableDriversRequest availableDriversRequest = new AvailableDriversRequest(latLng, radio);
        availableDriversRequest.getAvailableDrivers(this);
    }

    /**
     * this method is called when gets succeded on available drivers list
     */
    public void onAvailableDriverSuccess(List<AvailableDriver> availableDrivers) {

        this.availableDrivers = availableDrivers;
        for (int i = 0; i < availableDrivers.size(); i++){
            Driver driver = availableDrivers.get(i).info;
            LLELocation location = availableDrivers.get(i).location;

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.lat,location.Long))
                    .title(driver.name)
                    .snippet(driver.surname)); //TODO: mostrar info sobre autos
        }
    }

    /**
     * onServiceDidFailed shows Toast to inform the user the server has failed
     *
     * @param error to log in console
     */
    public void onServiceDidFailed(VolleyError error) {
        Log.e("error en la resupuesta", error.toString());
        updateInitialValues();
        Toast.makeText(getApplicationContext(), R.string.server_failed, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method is called when the where go button is pressed, it shows the activity that selects the trip
     *
     * @param view the button view
     */
    public void whereToGoButtonPressed(View view) {
        this.whereToGoButton.setVisibility(View.INVISIBLE);
        this.whereToGoView.setVisibility(View.VISIBLE);
    }

    /**
     * update the views to initial states
     */
    private void updateInitialValues(){
        this.whereToGoButton.setVisibility(View.VISIBLE);
        this.whereToGoView.setVisibility(View.INVISIBLE);
        this.setLoadingViewInvisible();
    }


    /**
     * this method is called once the client wants to start the trip
     *
     * @param view the button view
     */
    public void startTripButtonPressed(View view) {

        if(addressTo == null || addressFrom == null || addressFrom.equals("") || addressTo.equals("")){
            Toast.makeText(getApplicationContext(), R.string.complete_fields_error, Toast.LENGTH_SHORT).show();
            return;
        }

        //for trip service
        this.startPoint = new StartEndPointTrip();

        LLEAddress lleaddressStart = new LLEAddress();
        lleaddressStart.street = addressFrom.getAddressLine(0);

        TripLocation starttripLocation= new TripLocation();
        starttripLocation.lat = addressFrom.getLatitude();
        starttripLocation.lon = addressFrom.getLongitude();
        lleaddressStart.location = starttripLocation;

        this.startPoint.address = lleaddressStart;

        this.endPoint = new StartEndPointTrip();

        LLEAddress lleaddressEnd = new LLEAddress();
        lleaddressEnd.street = addressTo.getAddressLine(0);

        TripLocation endripLocation= new TripLocation();
        endripLocation.lat = addressTo.getLatitude();
        endripLocation.lon = addressTo.getLongitude();
        lleaddressEnd.location = endripLocation;

        this.endPoint.address = lleaddressEnd;


        this.setLoadingViewVisible();
        addressTo.getLatitude();
        Trajectory trajectory = new Trajectory();
        trajectory.origin_lat = addressFrom.getLatitude();
        trajectory.origin_long = addressFrom.getLongitude();
        trajectory.destination_lat = addressTo.getLatitude();
        trajectory.destination_long = addressTo.getLongitude();
        TrajectoryRequest trajectoryRequest = new TrajectoryRequest();
        trajectoryRequest.getWay(this, trajectory);

    }

    private void clearPolylinesIfShould(){
        mMap.clear();

    }


    /**
     * Renders the way on the map
     * @param steps all the steps returned from google api
     */
    public void onGetWaySuccess(ArrayList<Step> steps) {

        updateInitialValues();
        clearPolylinesIfShould();

        for (int i = 0; i < steps.size(); i++){

            Step step = steps.get(i);
            LatLng latLng1 = new LatLng(step.start_location.lat,step.start_location.lng);
            LatLng latLng2 = new LatLng(step.end_location.lat,step.end_location.lng);

            PolylineOptions polyline = new PolylineOptions()
                    .add(latLng1, latLng2)
                    .width(10)
                    .color(Color.BLUE);
            mMap.addPolyline(polyline);

        }

        this.tripSteps = steps;
        selectDriver();

    }

    /**
     * selects a driver to start a trip
     */
    private void selectDriver(){
        if(availableDrivers.size() <= 0){
            Toast.makeText(getApplicationContext(), R.string.no_drivers_available, Toast.LENGTH_SHORT).show();
            return;
        }

        int position=0;
        this.selectedDriver = availableDrivers.get(position).info;
        LLEFirebaseTokenRequest tokenRequest = new LLEFirebaseTokenRequest();
        tokenRequest.getFirebaseToken(this,this.selectedDriver.id);
    }

    /**
     * This method will comunicate the driver there is a new trip
     * @param driverComunicationToken the driver comunication token to send push notification
     */
    public void onGetFirebaseComunicationTokenSuccess(String driverComunicationToken) {
        Log.v(TAG,"Sending push notification.....");
        Notification notification = new Notification();
        notification.title = "Pasajero solicita nuevo viaje";
        notification.body = "";


        TripRequestData data = new TripRequestData();
        data.comunicationToken = FirebaseInstanceId.getInstance().getToken();
        data.type = "trip-request";
        data.name = client.name;
        data.surname = client.surname;
        data.address_to = addressTo.getAddressLine(0);
        data.address_from = addressFrom.getAddressLine(0);

        PushNotification pushNotification = new PushNotification();
        pushNotification.sender_id = "938482449732";
        pushNotification.to = driverComunicationToken;
        pushNotification.notification = notification;
        pushNotification.data = data;

        PushNotificationSenderRequest pushNotificationRequest = new PushNotificationSenderRequest();
        pushNotificationRequest.sendPushNotification(this,pushNotification);

        Toast.makeText(getApplicationContext(), R.string.notification_sent, Toast.LENGTH_SHORT).show();

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
            String driverComunicationToken = intent.getExtras().getString("comunicationToken");
            String tripState = intent.getExtras().getString("tripState"); //accepted or rejected

            if(tripState.equals("accepted")){
                String alertString = "Viaje aceptado, solicitamos nos indique cuando el comienza el viaje, apretando botón de comenzar viaje";
                showAlertDialog(alertString);
            }else{
                //TODO: VIAJE RECHAZADO
            }

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
                        sendTrip();
                        //TODO: mostrar boton de comenzar viaje
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void sendTrip(){

        TripData tripData = new TripData();

        Trip trip = new Trip();
        trip.driver = this.selectedDriver.id;
        trip.passenger = this.client.id;
        trip.route = this.tripSteps;

        trip.start = this.startPoint;
        trip.end = this.endPoint;

        //TODO: Pago hardcodeado por ahora hasta poner activity de pagos
        Paymethod paymethod = new Paymethod();
        paymethod.paymethod = "cash";
        PaymethodCashParameters payCash = new PaymethodCashParameters();
        payCash.type = "USD";
        paymethod.currency = "USD";
        paymethod.parameters = payCash;


        tripData.trip = trip;
        tripData.accepted_route = new HashMap();
        tripData.paymethod = paymethod;

        TripRequest tripRequest = new TripRequest(this.client.id);
        tripRequest.postTrip(this,tripData);
    }

    public void onTripRequestSuccess(String tripId) {
        sendAcceptTrip(tripId);
    }

    private void sendAcceptTrip(String tripId){
        StateTripRequest stateTripRequest = new StateTripRequest("accept",this.selectedDriver.id,tripId);
        stateTripRequest.sendStateTripRequest(this);
    }
    
}


