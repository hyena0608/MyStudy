package chatserver.message;

import java.time.LocalDateTime;
import java.util.Locale;

public class MessageFormBuilder {

    // 전부 한국으로 설정 .. 오직 한국을 위한..
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

    public MessageFormBuilder setMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public MessageFormBuilder setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public MessageFormBuilder setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public MessageFormBuilder setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
        return this;
    }

    public MessageFormBuilder setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    public MessageFormBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public MessageForm build() {
        return new MessageForm(localDateTime, messageType, sender, receiver,
                channelNumber, roomNumber, content, country, language);
    }
}
