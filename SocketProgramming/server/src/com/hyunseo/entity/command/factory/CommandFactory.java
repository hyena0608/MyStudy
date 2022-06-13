package com.hyunseo.entity.command.factory;

import com.hyunseo.entity.command.file.FileReceiverChatting;
import com.hyunseo.entity.command.file.FileSenderChatting;
import com.hyunseo.entity.command.file.FileType;
import com.hyunseo.entity.command.onetoone.OneToOneChatting;
import com.hyunseo.entity.command.onetoone.OneToOneConnectSetting;
import com.hyunseo.entity.command.onetoone.OneToOneDisconnectSetting;
import com.hyunseo.entity.command.room.RoomChatting;
import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.user.parser.UserSocketMessageParser;

public class CommandFactory {
    private Command command = null;

    public Command createCommand(String messageJson) {
        MessageObject messageObject = UserSocketMessageParser.toObject(messageJson);
        String messageType = messageObject.getUser().getUserCondition();

        if (messageType.equals(OneToOneConnectSetting.condition)) {
            command = OneToOneConnectSetting.getInstance();
        } else if (messageType.equals(OneToOneDisconnectSetting.condition)) {
            command = OneToOneDisconnectSetting.getInstance();
        } else if (messageType.equals(OneToOneChatting.condition)) {
            command = OneToOneChatting.getInstance();
        }

        if (messageType.equals(RoomChatting.condition)) {
            command = RoomChatting.getInstance();
        }

        if (messageType.equals(FileReceiverChatting.condition)) {
            command = FileReceiverChatting.getInstance();
        } else if (messageType.equals(FileSenderChatting.condition)) {
            command = FileSenderChatting.getInstance();
        }

        return command;
    }
}
