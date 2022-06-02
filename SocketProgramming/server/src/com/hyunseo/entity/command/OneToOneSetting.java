package com.hyunseo.entity.command;

import com.google.gson.Gson;
import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;

public class OneToOneSetting implements Command {

    private static volatile OneToOneSetting instance;
    public static final String condition = "ONETOONESETTING";
    private UserSocketMessageHandler userSocketMessageHandler = new UserSocketMessageHandler();


    private OneToOneSetting() {}

    public static OneToOneSetting getInstance() {
        if (instance == null) {
            synchronized (OneToOneSetting.class) {
                if (instance == null) {
                    instance = new OneToOneSetting();
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
