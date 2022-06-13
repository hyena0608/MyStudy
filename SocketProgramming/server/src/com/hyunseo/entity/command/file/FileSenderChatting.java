package com.hyunseo.entity.command.file;

import com.hyunseo.entity.command.base.Command;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;

import static com.hyunseo.entity.command.onetoone.OneToOne.ONETOONE_CHATTING;

public class FileSenderChatting implements Command {

    private static volatile FileSenderChatting instance;
    public static final String condition = String.valueOf(ONETOONE_CHATTING);
    private UserSocketMessageHandler userSocketMessageHandler = new UserSocketMessageHandler();

    public static FileSenderChatting getInstance() {
        if (instance == null) {
            synchronized (FileSenderChatting.class) {
                if (instance == null) {
                    instance = new FileSenderChatting();
                }
            }
        }
        return instance;
    }

    @Override
    public void send(String messageJson) {

    }
}
