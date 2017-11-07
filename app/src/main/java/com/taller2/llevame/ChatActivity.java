package com.taller2.llevame;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.taller2.llevame.Models.ChatMessage;

public class ChatActivity extends AppCompatActivity {

    private  FloatingActionButton fab;
    private  FirebaseUser currentUser;

    private static final String TAG = "ChatActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.fab = (FloatingActionButton)findViewById(R.id.fab);

        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        authenticateOnFirebase();
        configMessageButtonPressed();
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

                        // ...
                    }
                });

    }

    public void configMessageButtonPressed() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);


                //TODO: sacar estos valores hardcodeados
                ChatMessage chatMessage = new ChatMessage(input.getText().toString(),"Agustina","Oscar");

                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(chatMessage
                        );

                // Clear the input
                input.setText("");
            }
        });
    }

    /*private void displayChatMessages() {
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }*/
}
