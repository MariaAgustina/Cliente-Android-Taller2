package com.taller2.llevame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.taller2.llevame.Models.Chat;
import com.taller2.llevame.Models.ChatMessage;
import com.taller2.llevame.Models.Notification;
import com.taller2.llevame.Models.PushNotification;
import com.taller2.llevame.Models.TripRequestData;
import com.taller2.llevame.Views.LoadingView;
import com.taller2.llevame.Views.MessageAdapter;
import com.taller2.llevame.serviceLayerModel.ChatRequest;
import com.taller2.llevame.serviceLayerModel.PushNotificationSenderRequest;

public class ChatActivity extends BaseAtivity {

    private String senderUserName;
    private String receiverUserName;

    private  FloatingActionButton fab;
    private  FirebaseUser currentUser;
    private ArrayAdapter<ChatMessage> adapter;

    private Chat chat;
    private LoadingView loadingView;
    private String communicationToken;

    private static final String TAG = "ChatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.senderUserName =  (String)getIntent().getSerializableExtra("senderUserName");
        this.receiverUserName =  (String)getIntent().getSerializableExtra("receiverUserName");
        this.communicationToken =  (String)getIntent().getSerializableExtra("communicationToken");

        this.loadingView = new LoadingView();
        this.fab = (FloatingActionButton)findViewById(R.id.fab);
        authenticateOnFirebase();
        configMessageButtonPressed();
        getChatMessages();
    }

    private void authenticateOnFirebase(){
        //esta bien que este hardcodeado esto, porque voy a usar para postear el mismo usuario y contraseña
        //en realidad no hace falta loguearse pero esta bien que se loguee evita problemas de seguridad futuros
        FirebaseAuth.getInstance().signInWithEmailAndPassword("agustina@llevame.com", "12341234")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            currentUser = FirebaseAuth.getInstance().getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }

                    }
                });

    }

    public void configMessageButtonPressed() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                ChatMessage chatMessage = new ChatMessage(input.getText().toString(),senderUserName,receiverUserName);

                String chatName = getChatStringName();
                FirebaseDatabase.getInstance().getReference().child(chatName).push().setValue(chatMessage);

                chat.messages.add(chatMessage.messageText);
                chat.chatMessages.add(chatMessage);

                sendPushNotification(chatMessage);
                // Clear the input
                input.setText("");
                updateChatView();
            }
        });
    }

    private void updateChatView(){
        displayChatMessages();
    }

    private void sendPushNotification(ChatMessage chatMessage){

        Log.v(TAG,"Sending push notification.....");
        Notification notification = new Notification();
        notification.title = chatMessage.messageText;

        TripRequestData data = new TripRequestData();
        data.comunicationToken = FirebaseInstanceId.getInstance().getToken();
        data.type = "chat-message";

        PushNotification pushNotification = new PushNotification();
        pushNotification.sender_id = "938482449732";
        pushNotification.to = this.communicationToken;
        pushNotification.notification = notification;
        pushNotification.data = data;

        PushNotificationSenderRequest pushNotificationRequest = new PushNotificationSenderRequest();
        pushNotificationRequest.sendPushNotification(this,pushNotification);
    }


    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
        }
    };

    private void getChatMessages(){
        ChatRequest chatRequest = new ChatRequest();
        String chatName = getChatStringName();

        chatRequest.endponintUrl = chatName + ".json";
        chatRequest.getChat(this);
    }

    private String getChatStringName(){
        int compare = this.senderUserName.compareTo(this.receiverUserName);
        if (compare < 0){
            return this.senderUserName + "-" + this.receiverUserName;
        }
        return this.receiverUserName + "-" + this.senderUserName;

    }

    public void onGetChatSuccess(Chat chat) {
        this.loadingView.setLoadingViewInvisible(this);

        this.chat = chat;
        displayChatMessages();
    }

    private void displayChatMessages() {
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        MessageAdapter messageAdapter = new MessageAdapter(this,chat.chatMessages);

        listOfMessages.setAdapter(messageAdapter);
        listOfMessages.setSelection(this.chat.messages.size());


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
            String tripState = intent.getExtras().getString("tripState");
            if(tripState.equals("chat-message")){
                getChatMessages();
            }

        }
    };

}
