package clientserver.service.console.handler;

import clientserver.socket.UserSocket;
import clientserver.entity.command.factory.ChattingFactory;
import clientserver.entity.command.factory.SettingFactory;
import clientserver.entity.message.MessageObject;
import clientserver.service.base.MessageHandler;
import clientserver.service.console.parser.ConsoleMessageParserImpl;

import java.util.Scanner;

public class ConsoleMessageHandlerImpl implements MessageHandler, Runnable {
    private ConsoleMessageParserImpl consoleMessageParser = new ConsoleMessageParserImpl();
    private SettingFactory settingFactory = new SettingFactory();
    private ChattingFactory chattingFactory = new ChattingFactory();

    @Override
    public void handleMessage(String message) {
        if (isSettingType(message)) {
            settingFactory.createSetting(message)
                    .changeMySetting(message);
        } else if (isChattingType(message)) {
            MessageObject messageObject = consoleMessageParser.toObject(message);
            chattingFactory.createChatting(messageObject.getMessageType())
                    .sendChattingMessage(messageObject);
        }
    }

    private boolean isChattingType(String message) {
        if (message.length() == 0) {
            return false;
        }
        return message.charAt(0) != '/';
    }

    private boolean isSettingType(String message) {
        if (message.length() == 0) {
            return false;
        }
        return message.charAt(0) == '/';
    }

    @Override
    public void send(String consoleMessage) {
        System.out.println(consoleMessage);
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (UserSocket.getOut() != null) {
            handleMessage(sc.nextLine());
        }
    }

}
