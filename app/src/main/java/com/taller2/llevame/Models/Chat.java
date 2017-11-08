package com.taller2.llevame.Models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by amarkosich on 11/7/17.
 */

public class Chat {
    public ArrayList<String> messages;
    public ArrayList<ChatMessage> chatMessages;

    public Chat(HashMap dictionary){
        this.chatMessages = new ArrayList<ChatMessage>();
        this.messages = new ArrayList<String>();

        Iterator it = dictionary.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            parseChatMessage((LinkedTreeMap)pair.getValue());
            it.remove();
        }
    }

    private void parseChatMessage(LinkedTreeMap dictionary){
        Iterator it = dictionary.entrySet().iterator();
        ChatMessage chatMessage = new ChatMessage();
        while (it.hasNext()) {
            LinkedTreeMap.Entry pair = (LinkedTreeMap.Entry)it.next();
            String dictionaryKey = (String)pair.getKey();

            if(dictionaryKey.equals("messageText")) {
                chatMessage.messageText = (String)pair.getValue();
                this.messages.add(chatMessage.messageText);
            }else if(dictionaryKey.equals("userSender")) {
                chatMessage.userSender = (String)pair.getValue();
            }else if(dictionaryKey.equals("userReceiver")) {
                chatMessage.userReceiver = (String) pair.getValue();
            }else if(dictionaryKey.equals("messageTime")){
                //TODO: arreglar esto porque crashea
                //chatMessage.messageTime = (long)pair.getValue();

            }
            this.chatMessages.add(chatMessage);
            it.remove();
        }
    }
}
