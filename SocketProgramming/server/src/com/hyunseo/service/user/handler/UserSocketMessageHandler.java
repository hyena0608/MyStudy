package com.hyunseo.service.user.handler;

import com.hyunseo.entity.channel.Room;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.entity.message.MessageObjectBuilder;
import com.hyunseo.service.channel.handler.ChannelHandler;
import com.hyunseo.service.user.parser.UserSocketMessageParser;
import com.hyunseo.socket.user.UserSocket;

import java.io.IOException;
import java.util.Map;

public class UserSocketMessageHandler {

    private Map<String, Map<String, Room>> channelMap = ChannelHandler.getChannelMap();

    public void broadcastMessage(MessageObject messageObject) {
        String channelTitle = messageObject.getUser().getChannelTitle();
        String roomTitle = messageObject.getUser().getRoomTitle();
        String receiver = messageObject.getUser().getUsername();
        String messageJson = UserSocketMessageParser.toJson(messageObject);

        channelMap.get(channelTitle)
                .get(roomTitle)
                .getUserSocketList()
                .forEach(userSocket -> {
                    if (!userSocket.getUser().getUsername().equals(receiver)) {
                        try {
                            userSocket.getOut().writeUTF(messageJson);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    ;
                });
    }

    public void sendOneToOneMessage(MessageObject messageObject) {
        String channelTitle = messageObject.getUser().getChannelTitle();
        String roomTitle = messageObject.getUser().getRoomTitle();
        String receiver = messageObject.getUser().getUsername();
        String messageJson = UserSocketMessageParser.toJson(messageObject);

        channelMap
                .get(channelTitle)
                .get(roomTitle)
                .getUserSocketList()
                .forEach(userSocket -> {
                    if (userSocket.getUser().getUsername().equals(receiver)) {
                        try {
                            userSocket.getOut().writeUTF(messageJson);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
