package clientserver.service.console.parser;

import clientserver.socket.UserSocket;
import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.service.base.MessageParser;
import com.google.gson.Gson;

public class ConsoleMessageParserImpl implements MessageParser {
    Gson gson = new Gson();

    @Override
    public MessageObject toObject(String message) {
        MessageObject messageObject = new MessageObjectBuilder()
                .setMessageType(UserSocket.getUser().getUserCondition())
                .setContent(message)
                .build();
        return messageObject;
    }

    @Override
    public String toJson(MessageObject messageObject) {
        return gson.toJson(messageObject);
    }
}