package com.hyunseo.entity.command.onetoone;

import   com.google.gson.Gson;
import com.hyunseo.entity.command.base.Command;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.entity.message.MessageObjectBuilder;
//import com.hyunseo.service.onetoone.serversocket.handler.OneToOneServerSocketHandler;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;
import com.hyunseo.service.user.parser.UserSocketMessageParser;
import com.hyunseo.socket.onetoone.OneToOneServerSocket;

import static com.hyunseo.entity.command.onetoone.OneToOne.ONETOONE_CONNECT_SETTING;

public class OneToOneConnectSetting implements Command {

//    private OneToOneServerSocketHandler oneToOneServerSocketHandler = new OneToOneServerSocketHandler();
    private UserSocketMessageHandler userSocketMessageHandler = new UserSocketMessageHandler();
    private static volatile OneToOneConnectSetting instance;
    public static final String condition = String.valueOf(ONETOONE_CONNECT_SETTING);

    private OneToOneConnectSetting() {}

    public static OneToOneConnectSetting getInstance() {
        System.out.println("OneToOneConnectSetting getInstance");
        if (instance == null) {
            synchronized (OneToOneConnectSetting.class) {
                if (instance == null) {
                    instance = new OneToOneConnectSetting();
                }
            }
        }
        return instance;
    }

    @Override
    public void send(String messageJson) {
        // 넘겨야한다.
        OneToOneServerSocket oneToOneServerSocket = startOneToOneServerSocket();

        MessageObject messageObject = UserSocketMessageParser.toObject(messageJson);

        messageObject.getUser().setPort(oneToOneServerSocket.getPort());

        MessageObject messageObjectWithPort = new MessageObjectBuilder()
                .setMessageType(messageObject.getMessageType())
                .setUser(messageObject.getUser())
                .build();

        String s = new Gson().toJson(messageObjectWithPort);
        System.out.println("new Gson().toJson(messageObjectWithPort) = " + s);

        userSocketMessageHandler
                .sendPortForOneToOne(
                        messageObjectWithPort
                );
    }

    private OneToOneServerSocket startOneToOneServerSocket() {
        OneToOneServerSocket oneToOneServerSocket = new OneToOneServerSocket();
        oneToOneServerSocket.startServerSocket();
        new Thread(oneToOneServerSocket).start();

        return oneToOneServerSocket;
    }

}
