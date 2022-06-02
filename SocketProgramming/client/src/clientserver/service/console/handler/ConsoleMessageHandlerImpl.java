package clientserver.service.console.handler;

import clientserver.UserSocket;
import clientserver.entity.command.factory.ChattingFactory;
import clientserver.entity.command.factory.SettingFactory;
import clientserver.entity.message.MessageObject;
import clientserver.service.base.MessageHandler;
import clientserver.service.console.parser.ConsoleMessageParserImpl;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleMessageHandlerImpl implements MessageHandler, Runnable {
    private Scanner sc = new Scanner(System.in);
    private ConsoleMessageParserImpl messageParser = new ConsoleMessageParserImpl();
    private SettingFactory settingFactory = new SettingFactory();
    private ChattingFactory chattingFactory = new ChattingFactory();


    @Override
    public void receive() {

        String message = sc.nextLine();
        if (isSettingType(message)) {
            settingFactory.createSetting(message)
                    .changeMySetting ();
        } else if (isChattingType(message)) {
            MessageObject messageObject = messageParser.toObject(message);

            chattingFactory.createChatting()
                    .sendChattingMessage(messageObject);
        }
    }

    @Override
    public boolean isChattingType(String message) {
        return !message.substring(0, 1).equals("/");
    }

    @Override
    public boolean isSettingType(String message) {
        return message.substring(0, 1).equals("/");
    }

    @Override
    public void send(String consoleMessage) {
        System.out.println(consoleMessage);
    }

    @Override
    public void run() {
        while (UserSocket.getOut() != null) {
            // TODO : 설정 변경이 아닐시에..
            receive();
        }
    }

    public void sendUserToServerToJoinServer(String messageJson) {
        try {
            UserSocket.getOut().writeUTF(messageJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
