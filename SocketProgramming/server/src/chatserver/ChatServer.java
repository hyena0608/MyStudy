package chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public ChatServer() {

    }

    public void chatServerStart() {
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            ClientManager manager = new ClientManager();
            manager.run();

            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("[" + socket.getInetAddress() + "] 에서 서버에 접근합니다.");
                Client client = new Client(socket);
                manager.addClient(socket);
                client.run();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
