package com.taller2.llevame;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.taller2.llevame.Creational.FactoryActivities;
import com.taller2.llevame.Models.Client;
import com.taller2.llevame.Models.Notification;
import com.taller2.llevame.Models.PushNotification;
import com.taller2.llevame.Models.Step;
import com.taller2.llevame.Models.Trajectory;
import com.taller2.llevame.Models.TripRequestData;
import com.taller2.llevame.serviceLayerModel.PushNotificationSenderRequest;
import com.taller2.llevame.serviceLayerModel.TrajectoryRequest;

import java.util.ArrayList;

public class DriverMapsActivity extends MapsActivity {

    private View acceptTripButton;
    private View cancelTripButton;
    private Trajectory trajectory;
    private String clientComunicationToken;
    private Client client;
    private String clientUsername;
    private static final String TAG = "DriverMapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.trajectory = (Trajectory) getIntent().getSerializableExtra("trajectory");
        this.clientComunicationToken = (String)getIntent().getSerializableExtra("clientComunicationToken");
        this.client = (Client) getIntent().getSerializableExtra("client");
        this.clientUsername = (String) getIntent().getSerializableExtra("username");

        this.acceptTripButton = this.findViewById(R.id.acceptTripButton);
        this.cancelTripButton = this.findViewById(R.id.cancelTripButton);
        this.whereToGoButton.setVisibility(View.INVISIBLE);
        this.acceptTripButton.setVisibility(View.VISIBLE);
        this.cancelTripButton.setVisibility(View.VISIBLE);

        Log.v(TAG,this.trajectory.toString());
        TrajectoryRequest trajectoryRequest = new TrajectoryRequest();
        trajectoryRequest.getWay(this, this.trajectory);

    }

    /**
     * Renders the way on the map
     * @param steps all the steps returned from google api
     */
    public void onGetWaySuccess(ArrayList<Step> steps) {

        clearPolylinesIfShould();

        for (int i = 0; i < steps.size(); i++) {

            Step step = steps.get(i);
            LatLng latLng1 = new LatLng(step.start_location.lat, step.start_location.lng);
            LatLng latLng2 = new LatLng(step.end_location.lat, step.end_location.lng);

            PolylineOptions polyline = new PolylineOptions()
                    .add(latLng1, latLng2)
                    .width(10)
                    .color(Color.BLUE);
            mMap.addPolyline(polyline);

        }

    }

    /**
     * This method is called when the driver accepts the trip, it notifies the client and the app server
     */
    private void tripAccepted(){
        Log.v(TAG,"Sending push notification.....");
        Notification notification = new Notification();
        notification.title = "Chofer ha aceptado su viaje";
        notification.body = "";

        TripRequestData data = new TripRequestData();
        data.comunicationToken = FirebaseInstanceId.getInstance().getToken();
        data.type = "trip-accepted";

        PushNotification pushNotification = new PushNotification();
        pushNotification.sender_id = "938482449732";
        pushNotification.to = clientComunicationToken;
        pushNotification.notification = notification;
        pushNotification.data = data;

        PushNotificationSenderRequest pushNotificationRequest = new PushNotificationSenderRequest();
        pushNotificationRequest.sendPushNotification(this,pushNotification);

    }

    @Override
    public void showChat(){
        Log.v(TAG,"go to chat");
        FactoryActivities factoryActivities = new FactoryActivities();
        String driverUsername = client.username;
        factoryActivities.goToChatActivity(this,driverUsername,clientUsername,clientComunicationToken);
    }

    public void acceptTripButtonPressed(View view) {
        this.fab.setVisibility(View.VISIBLE);
        tripAccepted();
    }

    public void cancelTripButtonPressed(View view) {
        //TODO
    }

}
