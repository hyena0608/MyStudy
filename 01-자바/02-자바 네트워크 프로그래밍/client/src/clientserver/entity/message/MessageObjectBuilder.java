package clientserver.entity.message;

import clientserver.entity.user.User;

import java.time.LocalDateTime;
import java.util.Locale;

public class MessageObjectBuilder {

    private static final Locale locale = new Locale("ko", "KR");
    private final String country = locale.getCountry();
    private final String language = locale.getLanguage();
    private final LocalDateTime localDateTime = LocalDateTime.now();
    private String messageType;
    private String content;
    private User user;

    public MessageObjectBuilder setMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public MessageObjectBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public MessageObjectBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public MessageObject build() {
        return new MessageObject(localDateTime, messageType, content, country, language, user);
    }
}