package com.hyunseo.entity.command.onetoone;

import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;

import static com.hyunseo.entity.command.onetoone.OneToOne.*;

public class OneToOneDisconnectSetting implements Command {

    public static final String condition = String.valueOf(ONETOONE_DISCONNECT_SETTING);
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
        MessageObject messageObject = ReceiverChanger.change(messageJson);
        new UserSocketMessageHandler().sendOneToOneMessage(messageObject);
    }
}
