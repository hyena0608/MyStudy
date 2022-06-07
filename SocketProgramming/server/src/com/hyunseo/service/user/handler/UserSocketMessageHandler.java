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
                    }
                    ;
                });
    }

    // TODO : OneToOne 해당 소켓에 보내기로 수정해야함
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

    public void sendPortForOneToOne(MessageObject messageObject) {

        channelMap.get(messageObject.getUser().getChannelTitle())
                .get(messageObject.getUser().getRoomTitle())
                .getUserSocketList()
                .forEach(selectedUserSocket -> {
                    if (isUsername(selectedUserSocket, messageObject)) {
                        try {
                            MessageObject createdMessageObjectForUser = createUserMessageObject(selectedUserSocket, messageObject);
                            String messageJsonForUser = UserSocketMessageParser.toJson(createdMessageObjectForUser);
                            System.out.println("messageJsonForUser = " + messageJsonForUser);
                            selectedUserSocket.getOut().writeUTF(messageJsonForUser);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (isPartnerUsername(selectedUserSocket, messageObject)) {
                        try {
                            MessageObject createdMessageObjectForPartner = createPartnerMessageObject(selectedUserSocket, messageObject);
                            String messageJsonForPartner = UserSocketMessageParser.toJson(createdMessageObjectForPartner);
                            System.out.println("messageJsonForPartner = " + messageJsonForPartner);
                            selectedUserSocket.getOut().writeUTF(messageJsonForPartner);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    private MessageObject createPartnerMessageObject(UserSocket currentUserSocket, MessageObject messageObject) {
        currentUserSocket
                .getUser()
                .setUsername(messageObject.getUser().getPartnerUsername());

        currentUserSocket
                .getUser()
                .setPartnerUsername(messageObject.getUser().getUsername());

        currentUserSocket
                .getUser()
                .setPort(messageObject.getUser().getPort());

        return new MessageObjectBuilder()
                .setMessageType(messageObject.getMessageType())
                .setUser(currentUserSocket.getUser())
                .build();
    }

    private MessageObject createUserMessageObject(UserSocket currentUserSocket, MessageObject messageObject) {
        currentUserSocket
                .getUser()
                .setUsername(messageObject.getUser().getUsername());

        currentUserSocket
                .getUser()
                .setPartnerUsername(messageObject.getUser().getPartnerUsername());

        currentUserSocket
                .getUser()
                .setPort(messageObject.getUser().getPort());

        return new MessageObjectBuilder()
                .setMessageType(messageObject.getMessageType())
                .setUser(currentUserSocket.getUser())
                .build();
    }

    private boolean isPartnerUsername(UserSocket currentUserSocket, MessageObject messageObject) {
        String currentUsername = currentUserSocket.getUser().getUsername();
        String messagePartnerUsername = messageObject.getUser().getPartnerUsername();

        if (currentUsername.equals(messagePartnerUsername)) {
            return true;
        }
        return false;
    }

    private boolean isUsername(UserSocket currentUserSocket, MessageObject messageObject) {
        String currentUsername = currentUserSocket.getUser().getUsername();
        String messageUsername = messageObject.getUser().getUsername();

        if (currentUsername.equals(messageUsername)) {
            return true;
        }
        return false;
    }
}
