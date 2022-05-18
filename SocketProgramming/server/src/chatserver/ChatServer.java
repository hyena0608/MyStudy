package chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public void chatServerStart() {
        Socket socket;
        ClientManager manager = new ClientManager();
        new Thread(manager).start();
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            System.out.println("while 밖");
            while (true) {
                socket = serverSocket.accept();
                System.out.println("[" + socket.getRemoteSocketAddress() + "] 에서 서버에 접근합니다.");
                Client client = new Client(socket);
                new Thread(client).start();
                manager.addClient(client);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
