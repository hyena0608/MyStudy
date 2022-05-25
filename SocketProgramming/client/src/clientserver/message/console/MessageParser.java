package clientserver.message.console;

import clientserver.message.form.MessageForm;
import clientserver.utility.MyJsonParser;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MessageParser {
    ROOM("ROOM", jsonMessage -> new Room().print(jsonMessage)),
    ONTTOONE("ONTTOONE", jsonMessage -> new OneToOne().print(jsonMessage)),
    CLIENTINFO("CLIENTINFO", jsonMessage -> {});

    private String type;
    private Consumer<String> message;

    MessageParser(String type, Consumer<String> message) {
        this.type = type;
        this.message = message;
    }

    public void printMessage(String jsonMessage) {
        message.accept(jsonMessage);
    }

    public String getType() {
        return this.type;
    }

    private static final Map<String, MessageParser> messageTypeMap =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(
                            messageType -> messageType.getType()
                            , messageType -> messageType)));

    public static MessageParser findMessageType(String jsonMessage) {
        MessageForm messageForm = MyJsonParser.toObject(jsonMessage);
        return messageTypeMap.get(messageForm.getMessageType());
    }
}
