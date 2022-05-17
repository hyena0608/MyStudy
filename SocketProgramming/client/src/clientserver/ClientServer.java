package clientserver;

import java.io.IOException;
import java.net.Socket;

public class ClientServer {

    public void clientServerStart() {
        try {
            Socket socket = new Socket("localhost", 7777);
            ClientSender clientSender = new ClientSender(socket);
            System.out.println("[" + socket.getInetAddress() + "] 으로 접속하였습니다");

            clientSender.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
