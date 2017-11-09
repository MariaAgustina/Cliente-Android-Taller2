package com.taller2.llevame.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.taller2.llevame.Models.ChatMessage;
import com.taller2.llevame.R;

import java.util.ArrayList;

/**
 * Created by amarkosich on 11/8/17.
 */

public class MessageAdapter extends ArrayAdapter<ChatMessage> {

    public MessageAdapter(Context context, ArrayList<ChatMessage> chatMessages) {
        super(context, 0, chatMessages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ChatMessage chatMessage = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message, parent, false);
        }
        // Lookup view for data population

        TextView messageText = (TextView) convertView.findViewById(R.id.message_text);
        TextView messageUser = (TextView) convertView.findViewById(R.id.message_user);
        // Populate the data into the template view using the data object
        messageText.setText(chatMessage.messageText);
        messageUser.setText(chatMessage.userSender);
        // Return the completed view to render on screen
        return convertView;
    }
}