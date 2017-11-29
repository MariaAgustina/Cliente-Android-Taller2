package com.taller2.llevame.Models;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by amarkosich on 11/4/17.
 */

public class LLEFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "LLEFirebaseMessagin";
    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.v(TAG, "MESSAGE RECEIVED: " + remoteMessage);
        Log.v(TAG, "title " + remoteMessage.getNotification().getTitle());
        Log.v(TAG, "data" + remoteMessage.getData());

        if(remoteMessage.getData().get("type").equals("trip-request")){
            Intent intent = new Intent("NotificationData");
            intent.putExtra("comunicationToken", remoteMessage.getData().get("comunicationToken"));
            intent.putExtra("name", remoteMessage.getData().get("name"));
            intent.putExtra("surname", remoteMessage.getData().get("surname"));
            intent.putExtra("username", remoteMessage.getData().get("clientUsername"));
            intent.putExtra("address_from", remoteMessage.getData().get("address_from"));
            intent.putExtra("address_to", remoteMessage.getData().get("address_to"));
            intent.putExtra("lat_from", remoteMessage.getData().get("lat_from"));
            intent.putExtra("lon_from", remoteMessage.getData().get("lon_from"));
            intent.putExtra("lat_to", remoteMessage.getData().get("lat_to"));
            intent.putExtra("lon_to", remoteMessage.getData().get("lon_to"));

            broadcaster.sendBroadcast(intent);
        }else if(remoteMessage.getData().get("type").equals("trip-accepted")){
            Intent intent = new Intent("NotificationData");
            intent.putExtra("comunicationToken", remoteMessage.getData().get("comunicationToken"));
            intent.putExtra("tripState", "accepted");
            broadcaster.sendBroadcast(intent);
        }else if(remoteMessage.getData().get("type").equals("trip-rejected")){
            Intent intent = new Intent("NotificationData");
            intent.putExtra("tripState", "rejected");
            broadcaster.sendBroadcast(intent);
        }else if(remoteMessage.getData().get("type").equals("chat-message")) {
            Intent intent = new Intent("NotificationData");
            intent.putExtra("tripState", "chat-message");
            broadcaster.sendBroadcast(intent);
        }


        }


}
