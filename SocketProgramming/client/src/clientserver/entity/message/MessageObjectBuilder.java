package clientserver.entity.message;

import java.time.LocalDateTime;
import java.util.Locale;

public class MessageObjectBuilder {

    private static final Locale locale = new Locale("ko", "KR");
    private final String country = locale.getCountry();
    private final String language = locale.getLanguage();
    private final LocalDateTime localDateTime = LocalDateTime.now();
    private String messageType;
    private String sender;
    private String receiver;
    private String channelNumber;
    private String roomNumber;
    private String content;

    public MessageObjectBuilder setMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public MessageObjectBuilder setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public MessageObjectBuilder setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public MessageObjectBuilder setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
        return this;
    }

    public MessageObjectBuilder setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    public MessageObjectBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public MessageObject build() {
        return new MessageObject(localDateTime, messageType, sender, receiver, channelNumber, roomNumber, content, country, language);
    }
}
