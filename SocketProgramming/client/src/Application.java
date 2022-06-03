import clientserver.socket.UserSocket;
import clientserver.service.console.handler.ConsoleMessageHandlerImpl;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;

public class Application {

    public static void main(String[] args) {
        new UserSocket().init();
        new Thread(new ConsoleMessageHandlerImpl()).start();
        new Thread(new SocketMessageHandlerImpl()).start();
    }
}