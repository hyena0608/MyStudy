package com.hyunseo.entity.command.onetoone;

import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.user.parser.UserSocketMessageParser;

public class ReceiverChanger {
    public static MessageObject change(String messageJson) {
        MessageObject messageObject = UserSocketMessageParser.toObject(messageJson);
        String nowUsername = messageObject.getUser().getPartnerUsername();
        String nowPartnerUsername = messageObject.getUser().getUsername();
        messageObject.getUser().setUsername(nowUsername);
        messageObject.getUser().setPartnerUsername(nowPartnerUsername);
        return messageObject;
    }
}
