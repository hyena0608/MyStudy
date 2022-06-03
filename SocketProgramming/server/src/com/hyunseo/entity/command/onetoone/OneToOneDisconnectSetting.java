package com.hyunseo.entity.command.onetoone;

import com.hyunseo.entity.command.base.Command;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;

import static com.hyunseo.entity.command.onetoone.OneToOne.ONETOONE_CONNECT;
import static com.hyunseo.entity.command.onetoone.OneToOne.ONETOONE_DISCONNECT;

public class OneToOneDisconnectSetting implements Command {

    private final String condition = String.valueOf(ONETOONE_DISCONNECT);
    private static volatile OneToOneDisconnectSetting instance;

    private OneToOneDisconnectSetting() {}

    public static OneToOneDisconnectSetting getInstance() {
        if (instance == null) {
            synchronized (OneToOneDisconnectSetting.class) {
                if (instance == null) {
                    instance = new OneToOneDisconnectSetting();
                }
            }
        }
        return instance;
    }



    @Override
    public void send(String messageJson) {

    }
}
