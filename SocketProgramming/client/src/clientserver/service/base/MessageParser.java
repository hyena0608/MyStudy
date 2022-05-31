package clientserver.service.base;

import clientserver.entity.message.MessageObject;

public interface   MessageParser {
    abstract MessageObject toObject(String message);

    abstract String toJson(MessageObject messageObject);
}
