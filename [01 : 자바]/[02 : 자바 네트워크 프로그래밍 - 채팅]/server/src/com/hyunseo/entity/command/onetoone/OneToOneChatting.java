package com.hyunseo.entity.command.onetoone;

import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;

import static com.hyunseo.entity.command.onetoone.OneToOne.ONETOONE_CHATTING;

public class OneToOneChatting implements Command {

    private static volatile OneToOneChatting instance;
    public static final String condition = String.valueOf(ONETOONE_CHATTING);
    private UserSocketMessageHandler userSocketMessageHandler = new UserSocketMessageHandler();

    private OneToOneChatting() {
    }

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
        MessageObject messageObject = ReceiverChanger.change(messageJson);
        userSocketMessageHandler.sendOneToOneMessage(messageObject);
    }
}
