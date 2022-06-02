package com.hyunseo.entity.command.factory;

import com.google.gson.Gson;
import com.hyunseo.entity.command.OneToOneChatting;
import com.hyunseo.entity.command.OneToOneSetting;
import com.hyunseo.entity.command.RoomChatting;
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
        } else if (messageType.equals(OneToOneSetting.condition)) {
            command = OneToOneSetting.getInstance();
        }

        return command;
    }
}
