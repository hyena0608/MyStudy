package com.hyunseo.service.user.handler;

import com.hyunseo.entity.channel.Room;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.channel.handler.ChannelHandler;
import com.hyunseo.service.user.parser.UserSocketMessageParser;

import java.io.IOException;
import java.util.Map;

public class UserSocketMessageHandler {

    private Map<String, Map<String, Room>> channelMap = ChannelHandler.getChannelMap();

    public void broadcastMessage(MessageObject messageObject) {
        channelMap.get(messageObject.getUser().getChannelTitle())
                .get(messageObject.getUser().getRoomTitle())
                .getUserSocketList()
                .forEach(o -> {
                    if (!o.getUser().getUsername()
                            .equals(messageObject.getUser().getUsername())) {
                        try {
                            o.getOut().writeUTF(UserSocketMessageParser.toJson(messageObject));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    };
                });
    }

    public void sendOneToOneMessage(MessageObject messageObject) {
        channelMap.get(messageObject.getUser().getChannelTitle())
                .get(messageObject.getUser().getRoomTitle())
                .getUserSocketList()
                .forEach(o -> {
                    if (o.getUser().getUsername()
                            .equals(messageObject.getUser().getPartnerUsername())) {
                        try {
                            o.getOut().writeUTF(UserSocketMessageParser.toJson(messageObject));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void settingOneToOne(MessageObject messageObject) {
        channelMap.get(messageObject.getUser().getChannelTitle())
                .get(messageObject.getUser().getRoomTitle())
                .getUserSocketList()
                .forEach(o -> {
                    if (o.getUser().getUsername()
                            .equals(messageObject.getUser().getPartnerUsername())) {
                        try {
                            messageObject.getUser().setPartnerUsername(messageObject.getUser().getUsername());
                            messageObject.getUser().setUsername(o.getUser().getUsername());
                            System.out.println(o.getUser().getUsername() + "의 파트너는 " + o.getUser().getPartnerUsername() + "이다.");
                            System.out.println(messageObject.getUser().getUsername() + "의 파트너는 " + messageObject.getUser().getPartnerUsername() + "이다.");
                            o.getOut().writeUTF(UserSocketMessageParser.toJson(messageObject));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
