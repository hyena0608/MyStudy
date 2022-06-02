package com.hyunseo.entity.command.factory;

import com.google.gson.Gson;
import com.hyunseo.entity.command.onetoone.OneToOneChatting;
import com.hyunseo.entity.command.onetoone.OneToOneConnect;
import com.hyunseo.entity.command.room.RoomChatting;
import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;

public class CommandFactory {
    private Command command = null;

    public Command createCommand(String messageJson) {
        MessageObject messageObject = new Gson().fromJson(messageJson, MessageObject.class);
        String messageType = messageObject.getMessageType();

        if (messageType.equals(OneToOneChatting.condition)) {
            command = OneToOneChatting.getInstance();
        } else if (messageType.equals(RoomChatting.condition)) {
            command = RoomChatting.getInstance();
        } else if (messageType.equals(OneToOneConnect.condition)) {
            System.out.println("ONETOONE_CONNECT가 선택 되었습니다.");
            command = OneToOneConnect.getInstance();
        }

        return command;
    }
}
