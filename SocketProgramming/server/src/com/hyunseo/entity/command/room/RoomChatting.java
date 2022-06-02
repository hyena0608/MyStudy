package com.hyunseo.entity.command.room;

import com.google.gson.Gson;
import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;

public class RoomChatting implements Command {

    private static volatile RoomChatting instance;
    private UserSocketMessageHandler userSocketMessageHandler = new UserSocketMessageHandler();
    public static final String condition = "ROOM_CHATTING";

    private RoomChatting() {
    }

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
        userSocketMessageHandler
                .broadcastMessage(
                        new Gson().fromJson(messageJson, MessageObject.class)
                );
    }
}
