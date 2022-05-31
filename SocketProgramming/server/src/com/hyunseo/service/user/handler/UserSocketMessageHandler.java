package com.hyunseo.service.user.handler;

import com.google.gson.Gson;
import com.hyunseo.entity.channel.Room;
import com.hyunseo.entity.command.factory.CommandFactory;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.channel.handler.ChannelHandler;
import com.hyunseo.socket.UserSocket;

import java.io.IOException;
import java.util.Map;

public class UserSocketMessageHandler implements Runnable {

    private CommandFactory commandFactory = new CommandFactory();
    private Gson gson = new Gson();
    private Map<String, Map<String, Room>> channelMap = ChannelHandler.getChannelMap();

    private void receive() {

        for (Map<String, Room> roomMap : channelMap.values()) {
            for (Room room : roomMap.values()) {
                for (UserSocket userSocket : room.getUserSocketList()) {
                    try {
                        String messageJson = userSocket.getIn().readUTF();
                        commandFactory.createCommand(messageJson).send(messageJson);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void broadcastMessage(MessageObject messageObject) {
        channelMap.get(messageObject.getUser().getChannelTitle())
                .get(messageObject.getUser().getRoomTitle())
                .getUserSocketList()
                .forEach(o -> {
                    if (!o.getUser().getUsername()
                            .equals(messageObject.getUser().getUsername())) {
                        try {
                            o.getOut().writeUTF(gson.toJson(messageObject));
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
                            o.getOut().writeUTF(gson.toJson(messageObject));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void run() {
        while (true) {
            receive();
        }
    }
}
