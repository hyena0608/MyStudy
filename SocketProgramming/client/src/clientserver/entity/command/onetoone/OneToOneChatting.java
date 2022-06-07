package clientserver.entity.command.onetoone;

import clientserver.socket.SocketType;
import clientserver.socket.UserSocket;
import clientserver.entity.command.base.Chatting;
import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.service.console.handler.ConsoleMessageHandlerImpl;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;
import com.google.gson.Gson;

import static clientserver.entity.command.onetoone.OneToOneType.ONETOONE_CHATTING;
import static clientserver.socket.SocketType.ONETOONE_SOCKET;


public class OneToOneChatting implements Chatting {

    private static volatile OneToOneChatting instance;
    public static final String condition = String.valueOf(ONETOONE_CHATTING);
    private ConsoleMessageHandlerImpl consoleMessageHandler = new ConsoleMessageHandlerImpl();
    private SocketMessageHandlerImpl socketMessageHandler = new SocketMessageHandlerImpl();
    private SocketType socketType = ONETOONE_SOCKET;

    private OneToOneChatting() {
    }

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
        MessageObject oneToOneMessageObject =
                new MessageObjectBuilder()
                        .setMessageType(UserSocket.getUser().getUserCondition())
                        .setContent(messageObject.getContent())
                        .setUser(UserSocket.getUser())
                        .build();

        socketMessageHandler.send(String.valueOf(ONETOONE_SOCKET), new Gson().toJson(oneToOneMessageObject));
    }

    @Override
    public void consoleMessage(MessageObject messageObject) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer
                .append("[귓속말 - ")
                .append(messageObject.getUser())
                .append("] : ")
                .append(messageObject.getContent());

        consoleMessageHandler.send(String.valueOf(socketType), stringBuffer.toString());
    }
}