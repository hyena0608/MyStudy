package com.hyunseo.entity.command;

import com.google.gson.Gson;
import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;

public class OneToOneChatting implements Command {

    private static volatile OneToOneChatting instance;
    public static final String condition = "ONETOONE";
    private UserSocketMessageHandler userSocketMessageHandler = new UserSocketMessageHandler();

    private OneToOneChatting() {}

    public static OneToOneChatting getInstance() {
        if (instance == null) {
            synchronized (OneToOneChatting.class) {
                if (instance == null) {
                    instance = new OneToOneChatting();
                }
            }
        }
        return instance;
    }

    @Override
    public void send(String messageJson) {
        MessageObject messageObject = new Gson().fromJson(messageJson, MessageObject.class);
        userSocketMessageHandler.sendOneToOneMessage(messageObject);
    }
}
