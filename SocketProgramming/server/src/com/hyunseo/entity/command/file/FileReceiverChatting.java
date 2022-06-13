package com.hyunseo.entity.command.file;

import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.entity.message.MessageObjectBuilder;
import com.hyunseo.entity.user.User;
import com.hyunseo.service.channel.handler.ChannelHandler;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;
import com.hyunseo.service.user.parser.UserSocketMessageParser;

import static com.hyunseo.entity.command.file.FileType.FILE_RECEIVE_CHATTING;

public class FileReceiverChatting implements Command {

    private static volatile FileReceiverChatting instance;
    public static final String condition = String.valueOf(FILE_RECEIVE_CHATTING);
    private User findNowUser;

    public static FileReceiverChatting getInstance() {
        if (instance == null) {
            synchronized (FileReceiverChatting.class) {
                if (instance == null) {
                    instance = new FileReceiverChatting();
                }
            }
        }
        return instance;
    }

    @Override
    public void send(String messageJson) {
        System.out.println(messageJson);
        MessageObject messageObject = UserSocketMessageParser.toObject(messageJson);

        String channel = messageObject.getUser().getChannelTitle();
        String room = messageObject.getUser().getRoomTitle();
        String needUsername = messageObject.getUser().getPartnerUsername();

        ChannelHandler.getChannelMap()
                .get(channel)
                .get(room)
                .getUserSocketList()
                .forEach(userSocket -> {
                    if (userSocket.getUser().getUsername().equals(needUsername)) {
                        this.findNowUser = userSocket.getUser();
                    }
                });

        MessageObject messageObjectForReceiver = new MessageObjectBuilder()
                .setUser(this.findNowUser)
                .setMessageType(condition)
                .build();

        new UserSocketMessageHandler().sendOneToOneMessage(messageObjectForReceiver);
    }
}
