package clientserver.service.base;

public interface MessageHandler {

    abstract void handleMessage(String message);

    abstract void send(String type, String message);

}
