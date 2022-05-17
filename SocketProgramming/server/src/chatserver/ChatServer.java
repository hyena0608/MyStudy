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

            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("[" + socket.getInetAddress() + "] 에서 서버에 접근합니다.");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
