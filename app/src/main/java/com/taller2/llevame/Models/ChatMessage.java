package com.taller2.llevame.Models;

import java.util.Date;

/**
 * Created by amarkosich on 11/6/17.
 */

public class ChatMessage {
    public String messageText;
    public String userSender;
    public String userReceiver;

    public long messageTime;

    public ChatMessage(String messageText, String userSender, String userReceiver) {
        this.messageText = messageText;
        this.userSender = userSender;
        this.userReceiver = userReceiver;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

}
