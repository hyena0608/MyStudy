package com.hyunseo.service.user.parser;

import com.google.gson.Gson;
import com.hyunseo.entity.message.MessageObject;

public class UserSocketMessageParser  {

    private static Gson gson = new Gson();

    public static MessageObject toObject(String message) {
        return gson.fromJson(message, MessageObject.class);
    }

    public static String toJson(MessageObject messageObject) {
        return gson.toJson(messageObject);
    }
}
