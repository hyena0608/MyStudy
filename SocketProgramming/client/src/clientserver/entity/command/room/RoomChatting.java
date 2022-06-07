package clientserver.entity.command.room;

import clientserver.service.socket.parser.SocketMessageParserImpl;
import clientserver.socket.SocketType;
import clientserver.socket.UserSocket;
import clientserver.entity.command.base.Chatting;
import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.service.console.handler.ConsoleMessageHandlerImpl;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;

import static clientserver.entity.command.room.Room.ROOM_CHATTING;
import static clientserver.socket.SocketType.USER_SOCKET;

public class RoomChatting implements Chatting {

    private static volatile RoomChatting instance;
    public static final String condition = String.valueOf(ROOM_CHATTING);
    private SocketType socketType = USER_SOCKET;
    private ConsoleMessageHandlerImpl consoleMessageHandler = new ConsoleMessageHandlerImpl();
    private SocketMessageHandlerImpl socketMessageHandler = new SocketMessageHandlerImpl();
    private SocketMessageParserImpl socketMessageParser = new SocketMessageParserImpl();

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

        String messageJson = socketMessageParser.toJson(roomMessageObject);

        socketMessageHandler.send(String.valueOf(socketType), messageJson);
    }

    @Override
    public void consoleMessage(MessageObject messageObject) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer
                .append("[")
                .append(messageObject.getUser().getUsername())
                .append("] : ")
                .append(messageObject.getContent());

        consoleMessageHandler.send(String.valueOf(socketType), stringBuffer.toString());
    }
}
