package com.hyunseo.entity.command.factory;

import com.google.gson.Gson;
import com.hyunseo.entity.command.onetoone.OneToOneChatting;
import com.hyunseo.entity.command.onetoone.OneToOneConnectSetting;
import com.hyunseo.entity.command.onetoone.OneToOneDisconnectSetting;
import com.hyunseo.entity.command.room.RoomChatting;
import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;

import static com.hyunseo.entity.command.onetoone.OneToOne.*;

public class CommandFactory {
    private Command command = null;

    public Command createCommand(String messageJson) {
        MessageObject messageObject = new Gson().fromJson(messageJson, MessageObject.class);
        String messageType = messageObject.getMessageType();

        if (messageType.equals(ONETOONE_CONNECT.toString())) {
            command = OneToOneConnectSetting.getInstance();
        } else if (messageType.equals(ONETOONE_DISCONNECT.toString())) {
            command = OneToOneDisconnectSetting.getInstance();
        } else if (messageType.equals(ONETOONE_CHATTING.toString())) {
            command = OneToOneChatting.getInstance();
        }

        if (messageType.equals(RoomChatting.condition)) {
            command = RoomChatting.getInstance();
        }

        return command;
    }
}
