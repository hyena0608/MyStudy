package chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private ClientManager clientManager;

    public void chatServerStart() {
        Socket socket;
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while (true) {
                socket = serverSocket.accept();
                setClientManagerAndStart(socket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setClientManagerAndStart(Socket socket) {
        clientManager = new ClientManager(socket);
        new Thread(clientManager).start();
        System.out.println("[" + socket.getRemoteSocketAddress() + "] 에서 서버에 접근합니다.");
    }
}
