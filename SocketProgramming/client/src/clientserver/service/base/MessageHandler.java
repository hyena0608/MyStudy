package clientserver.service.base;

public interface MessageHandler {

    abstract void receive();

    abstract void send(String message);

    abstract boolean isChattingType(String message);

    abstract boolean isSettingType(String message);
}
