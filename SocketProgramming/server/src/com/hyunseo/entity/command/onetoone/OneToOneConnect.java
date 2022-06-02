package com.hyunseo.entity.command.onetoone;

import com.google.gson.Gson;
import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;

public class OneToOneConnect implements Command {

    private static volatile OneToOneConnect instance;
    public static final String condition = "ONETOONE_CONNECT";
    private UserSocketMessageHandler userSocketMessageHandler = new UserSocketMessageHandler();


    private OneToOneConnect() {}

    public static OneToOneConnect getInstance() {
        if (instance == null) {
            synchronized (OneToOneConnect.class) {
                if (instance == null) {
                    instance = new OneToOneConnect();
                }
            }
        }
        return instance;
    }

    @Override
    public void send(String messageJson) {
        userSocketMessageHandler
                .settingOneToOne(
                        new Gson().fromJson(messageJson, MessageObject.class)
                );
    }
}
