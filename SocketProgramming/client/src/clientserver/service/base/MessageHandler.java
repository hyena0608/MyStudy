package clientserver.service.base;

public interface MessageHandler {

    abstract void receive();

    abstract void send(String message);

}
