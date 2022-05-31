package clientserver.service.socket.parser;

import clientserver.entity.message.MessageObject;
import clientserver.service.base.MessageParser;
import com.google.gson.Gson;

public class SocketMessageParserImpl implements MessageParser {

    private Gson gson = new Gson();

    @Override
    public MessageObject toObject(String messageJson) {
        return gson.fromJson(messageJson, MessageObject.class);
    }

    @Override
    public String toJson(MessageObject messageObject) {
        return null;
    }

}
