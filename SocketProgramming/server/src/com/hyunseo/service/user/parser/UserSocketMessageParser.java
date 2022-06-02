package com.hyunseo.service.user.parser;

import com.google.gson.Gson;
import com.hyunseo.entity.message.MessageObject;

public class UserSocketMessageParser  {

    private Gson gson = new Gson();

    public MessageObject toObject(String message) {
        return gson.fromJson(message, MessageObject.class);
    }

    public String toJson(MessageObject messageObject) {
        return gson.toJson(messageObject);
    }
}
