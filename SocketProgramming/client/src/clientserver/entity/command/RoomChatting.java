package clientserver.entity.command;

import clientserver.UserSocket;
import clientserver.entity.command.base.Chatting;
import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.service.console.handler.ConsoleMessageHandlerImpl;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;
import com.google.gson.Gson;

public class RoomChatting implements Chatting {

    private static volatile RoomChatting instance;
    public static final String condition = "ROOMCHATTING";
    private ConsoleMessageHandlerImpl consoleMessageHandler = new ConsoleMessageHandlerImpl();
    private SocketMessageHandlerImpl socketMessageHandler = new SocketMessageHandlerImpl();

    private RoomChatting() {}

    public static RoomChatting getInstance() {
        if (instance == null) {
            synchronized (RoomChatting.class) {
                if (instance == null) {
                    instance = new RoomChatting();
                }
            }
        }
        return instance;
    }

    @Override
    public void sendChattingMessage(MessageObject messageObject) {
        MessageObject roomMessageObject = new MessageObjectBuilder()
                .setUser(UserSocket.getUser())
                .setContent(messageObject.getContent())
                .setMessageType(UserSocket.getUser().getUserCondition())
                .build();

        socketMessageHandler.send(new Gson().toJson(roomMessageObject));
    }

    @Override
    public void consoleMessage(MessageObject messageObject) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer
                .append("[")
                .append(messageObject.getUser().getUsername())
                .append("] : ")
                .append(messageObject.getContent());

        consoleMessageHandler.send(stringBuffer.toString());
    }
}