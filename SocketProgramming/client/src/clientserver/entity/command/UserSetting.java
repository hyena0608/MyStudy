package clientserver.entity.command;

import clientserver.UserSocket;
import clientserver.entity.command.base.Setting;
import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.service.console.handler.ConsoleMessageHandlerImpl;
import clientserver.service.console.parser.ConsoleMessageParserImpl;

public class UserSetting implements Setting {

    private static volatile UserSetting instance;
    private static final String condition = "UserSetting";

    public static UserSetting getInstance() {
        if (instance == null) {
            synchronized (UserSetting.class) {
                if (instance == null) {
                    instance = new UserSetting();
                }
            }
        }
        return instance;
    }

    @Override
    public void changeMySetting() {
        ConsoleMessageHandlerImpl consoleMessageHandler = new ConsoleMessageHandlerImpl();
        ConsoleMessageParserImpl consoleMessageParser = new ConsoleMessageParserImpl();

        MessageObject messageObject = new MessageObjectBuilder()
                .setMessageType(UserSocket.getUser().getUserCondition())
                .setUser(UserSocket.getUser())
                .build();

        String messageJson = consoleMessageParser.toJson(messageObject);
        consoleMessageHandler.sendUserToServerToJoinServer(messageJson);
    }
}
