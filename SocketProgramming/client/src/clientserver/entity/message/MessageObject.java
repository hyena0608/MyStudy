package clientserver.entity.message;

import clientserver.entity.user.User;

import java.time.LocalDateTime;

public class  MessageObject {
    private final LocalDateTime localDateTime;
    private final String messageType;
    private final String content;
    private final String country;
    private final String language;
    private User user;

    public MessageObject(LocalDateTime localDateTime, String messageType, String content, String country, String language, User user) {
        this.localDateTime = localDateTime;
        this.messageType = messageType;
        this.content = content;
        this.country = country;
        this.language = language;
        this.user = user;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getMessageType() {
        return messageType;
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

    public User getUser() {
        return this.user;
    }
}
