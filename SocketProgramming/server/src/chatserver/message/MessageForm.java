package chatserver.message;

import java.time.LocalDateTime;

public class MessageForm {
    private final LocalDateTime localDateTime;
    private final String messageType;
    private final String sender;
    private final String receiver;
    private final String channelNumber;
    private final String roomNumber;
    private final String content;
    private String country;
    private String language;

    public MessageForm(LocalDateTime localDateTime, String messageType, String sender, String receiver, String channelNumber, String roomNumber, String content, String country, String language) {
        this.localDateTime = localDateTime;
        this.messageType = messageType;
        this.sender = sender;
        this.receiver = receiver;
        this.channelNumber = channelNumber;
        this.roomNumber = roomNumber;
        this.content = content;
        this.country = country;
        this.language = language;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getContent() {
        return content;
    }

    public String getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }
}
