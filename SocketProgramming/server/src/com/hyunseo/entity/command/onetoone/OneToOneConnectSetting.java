package com.hyunseo.entity.command.onetoone;

import com.hyunseo.entity.command.base.Command;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;

import static com.hyunseo.entity.command.onetoone.OneToOne.ONETOONE_CONNECT_SETTING;

public class OneToOneConnectSetting implements Command {

    private UserSocketMessageHandler userSocketMessageHandler = new UserSocketMessageHandler();
    private static volatile OneToOneConnectSetting instance;
    public static final String condition = String.valueOf(ONETOONE_CONNECT_SETTING);

    private OneToOneConnectSetting() {}

    public static OneToOneConnectSetting getInstance() {
        System.out.println("OneToOneConnectSetting getInstance");
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

    }

}
