package clientserver.entity.command.base;

import clientserver.entity.message.MessageObject;

public interface Chatting {
    abstract void sendMessage(MessageObject messageObject);

    abstract void consoleMessage(MessageObject messageObject);
}
