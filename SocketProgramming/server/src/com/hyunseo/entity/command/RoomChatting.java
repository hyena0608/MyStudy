package com.hyunseo.entity.command;

import com.google.gson.Gson;
import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;

public class RoomChatting implements Command {

    private static volatile RoomChatting instance;
    private UserSocketMessageHandler userSocketMessageHandler = new UserSocketMessageHandler();
    public static final String condition = "ROOM";

    private RoomChatting() {}

    public static RoomChatting getInstance() {
        if (instance == null) {
            synchronized (RoomChatting.class) {
                if (instance == null) {
                    instance = new RoomChatting();
                }
            }
        }
        return instance;
    }


    @Override
    public void send(String messageJson) {
        MessageObject messageObject = new Gson().fromJson(messageJson, MessageObject.class);
        userSocketMessageHandler.broadcastMessage(messageObject);
    }
}
