package com.hyunseo.entity.command.onetoone;

import com.google.gson.Gson;
import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;

public class OneToOneConnectSetting implements Command {

    private static volatile OneToOneConnectSetting instance;
    public static final String condition = "ONETOONE_CONNECT";
    private UserSocketMessageHandler userSocketMessageHandler = new UserSocketMessageHandler();


    private OneToOneConnectSetting() {}

    public static OneToOneConnectSetting getInstance() {
        if (instance == null) {
            synchronized (OneToOneConnectSetting.class) {
                if (instance == null) {
                    instance = new OneToOneConnectSetting();
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
