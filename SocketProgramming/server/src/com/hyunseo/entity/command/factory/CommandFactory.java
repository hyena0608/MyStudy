package com.hyunseo.entity.command.factory;

import com.google.gson.Gson;
import com.hyunseo.entity.command.OneToOneChatting;
import com.hyunseo.entity.command.RoomChatting;
import com.hyunseo.entity.command.UserSetting;
import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;

public class CommandFactory {
    private Command command = null;

    public Command createCommand(String messageJson) {
        MessageObject messageObject = new Gson().fromJson(messageJson, MessageObject.class);

        if (messageObject.getMessageType().equals(OneToOneChatting.condition)) {
            command = OneToOneChatting.getInstance();
        } else if (messageObject.getMessageType().equals(RoomChatting.condition)) {
            command = RoomChatting.getInstance();
        } else if (messageObject.getMessageType().equals(UserSetting.condition)) {
            command = UserSetting.getInstance();
        }

        return command;
    }
}
