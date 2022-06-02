package clientserver.service.socket.handler;

import clientserver.UserSocket;
import clientserver.entity.command.factory.ChattingFactory;
import clientserver.entity.message.MessageObject;
import clientserver.service.base.MessageHandler;
import clientserver.service.socket.parser.SocketMessageParserImpl;

import java.io.IOException;

public class SocketMessageHandlerImpl implements MessageHandler, Runnable {
    private SocketMessageParserImpl messageParser = new SocketMessageParserImpl();
    private ChattingFactory chattingFactory = new ChattingFactory();

    @Override
    public void receive()  {
        try {
            String messageJson = UserSocket.getIn().readUTF();
            MessageObject messageObject = messageParser.toObject(messageJson);

            chattingFactory.createChatting()
                    .consoleMessage(messageObject);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isChattingType(String message) {
        return !message.substring(0, 0).equals("/");
    }

    @Override
    public boolean isSettingType(String message) {
        return message.substring(0, 0).equals("/");
    }

    @Override
    public void send(String messageJson) {
        try {
            UserSocket.getOut().writeUTF(messageJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (UserSocket.getSocket().isConnected()) {
            receive();
        }
    }

}
