package clientserver.service.socket.handler;

import clientserver.entity.command.factory.SocketFactory;
import clientserver.socket.OneToOneUserSocket;
import clientserver.socket.Socket;
import clientserver.socket.UserSocket;
import clientserver.entity.command.user.UserSetting;
import clientserver.entity.command.factory.ChattingFactory;
import clientserver.entity.command.factory.SettingFactory;
import clientserver.entity.message.MessageObject;
import clientserver.service.base.MessageHandler;
import clientserver.service.socket.parser.SocketMessageParserImpl;

import java.io.IOException;

public class SocketMessageHandlerImpl implements MessageHandler, Runnable {
    private SocketMessageParserImpl socketMessageParser = new SocketMessageParserImpl();
    private ChattingFactory chattingFactory = new ChattingFactory();
    private SettingFactory settingFactory = new SettingFactory();
    private SocketFactory socketFactory = new SocketFactory();

    @Override
    public void handleMessage(String messageJson) {
        MessageObject messageObject = socketMessageParser.toObject(messageJson);

        if (messageObject.getMessageType().contains("CHATTING")) {
            chattingFactory.createChatting()
                    .consoleMessage(messageObject);
        } else if (messageObject.getMessageType().contains("SETTING")) {
            settingFactory.createSetting(messageObject.getMessageType())
                    .changeMySetting(messageJson);
        }
    }

    @Override
    public void send(String type, String messageJson) {
        try {
            Socket socket = socketFactory.createSocket(type);
            socket.takeOut().writeUTF(messageJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendUserSetting(String message) {
        settingFactory
                .createSetting(UserSetting.condition)
                .changeMySetting(message);
    }

    public void sendUserToServerToJoinServer(String messageJson) {
        try {
            UserSocket.getOut().writeUTF(messageJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (UserSocket.getSocket().isConnected()) {
                try {
                    String message = UserSocket.getIn().readUTF();
                    handleMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (OneToOneUserSocket.getSocket() != null) {
                if (OneToOneUserSocket.getSocket().isConnected()) {
                    try {
                        String message = OneToOneUserSocket.getIn().readUTF();
                        handleMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
