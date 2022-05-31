package clientserver.entity.command;

import clientserver.UserSocket;
import clientserver.entity.command.base.Chatting;
import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.entity.user.User;
import clientserver.service.console.handler.ConsoleMessageHandlerImpl;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;
import com.google.gson.Gson;

public class OneToOneChatting implements Chatting {

    private static volatile OneToOneChatting instance;
    public static final String condition = "ONETOONE";
    private ConsoleMessageHandlerImpl consoleMessageHandler = new ConsoleMessageHandlerImpl();
    private SocketMessageHandlerImpl socketMessageHandler = new SocketMessageHandlerImpl();

    private OneToOneChatting() {}

    public static OneToOneChatting getInstance() {
        if (instance == null) {
            synchronized (OneToOneChatting.class) {
                if (instance == null) {
                    instance = new OneToOneChatting();
                }
            }
        }
        return instance;
    }

    @Override
    public void sendChattingMessage(MessageObject messageObject) {
        MessageObject oneToOneMessageObject = new MessageObjectBuilder()
                .setMessageType(UserSocket.getUser().getUserCondition())
                .setContent(messageObject.getContent())
                .build();

        socketMessageHandler.send(new Gson().toJson(oneToOneMessageObject));
    }

    @Override
    public void consoleMessage(MessageObject messageObject) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer
                .append("[귓속말 - ")
                .append(messageObject.getUser())
                .append("] : ")
                .append(messageObject.getContent());

        consoleMessageHandler.send(stringBuffer.toString());
    }
}
