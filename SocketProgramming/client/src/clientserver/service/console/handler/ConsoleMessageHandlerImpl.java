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
    private Scanner sc = new Scanner(System.in);

    @Override
    public void handleMessage(String consoleMessage) {
        if (isSettingType(consoleMessage)) {
            settingFactory.createSetting(consoleMessage)
                    .changeMySetting(consoleMessage);
        } else if (isChattingType(consoleMessage)) {
            MessageObject messageObject = consoleMessageParser.toObject(consoleMessage);
            chattingFactory.createChatting(messageObject.getMessageType())
                    .sendMessage(messageObject);
        }
    }

    private boolean isChattingType(String consoleMessage) {
        if (consoleMessage.length() == 0) {
            return false;
        }
        return !consoleMessage.startsWith("/");
    }

    private boolean isSettingType(String consoleMessage) {
        if (consoleMessage.length() == 0) {
            return false;
        }
        return consoleMessage.startsWith("/");
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
