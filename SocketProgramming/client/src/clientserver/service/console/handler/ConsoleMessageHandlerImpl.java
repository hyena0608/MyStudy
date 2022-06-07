package clientserver.service.console.handler;

import clientserver.socket.UserSocket;
import clientserver.entity.command.factory.ChattingFactory;
import clientserver.entity.command.factory.SettingFactory;
import clientserver.entity.message.MessageObject;
import clientserver.service.base.MessageHandler;
import clientserver.service.console.parser.ConsoleMessageParserImpl;

import java.util.Scanner;

public class ConsoleMessageHandlerImpl implements MessageHandler, Runnable {
    private Scanner sc = new Scanner(System.in);
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

            chattingFactory.createChatting()
                    .sendChattingMessage(messageObject);
        }
    }

    private boolean isChattingType(String message) {
        if (message.length() == 0) {
            return false;
        }
        return !message.substring(0, 1).equals("/");
    }

    private boolean isSettingType(String message) {
        if (message.length() == 0) {
            return false;
        }
        return message.substring(0, 1).equals("/");
    }

    @Override
    public void send(String consoleMessage) {
        System.out.println(consoleMessage);
    }

    @Override
    public void run() {
        while (UserSocket.getOut() != null) {
            handleMessage(sc.nextLine());
        }
    }

}
