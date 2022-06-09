package clientserver.service.socket.handler;

import clientserver.socket.UserSocket;
import clientserver.entity.command.user.UserSetting;
import clientserver.entity.command.factory.ChattingFactory;
import clientserver.entity.command.factory.SettingFactory;
import clientserver.entity.message.MessageObject;
import clientserver.service.base.MessageHandler;
import clientserver.service.socket.parser.SocketMessageParserImpl;
import com.google.gson.Gson;

import java.io.IOException;

public class SocketMessageHandlerImpl implements MessageHandler, Runnable {
    private SocketMessageParserImpl socketMessageParser = new SocketMessageParserImpl();
    private ChattingFactory chattingFactory = new ChattingFactory();
    private SettingFactory settingFactory = new SettingFactory();

    @Override
    public void handleMessage(String messageJson) {
        MessageObject messageObject = socketMessageParser.toObject(messageJson);
        String messageType = messageObject.getMessageType();

        System.out.println(new Gson().toJson(messageObject));

        if (messageObject.getMessageType().contains("CHATTING")) {
            chattingFactory.createChatting(messageType)
                    .consoleMessage(messageObject);
        } else if (messageObject.getMessageType().contains("SETTING")) {
            settingFactory.createSetting(messageObject.getMessageType())
                    .changeMySetting(messageJson);
        }
    }

    @Override
    public void send(String messageJson) {
        try {
            UserSocket.getOut().writeUTF(messageJson);
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
        }
    }

}
