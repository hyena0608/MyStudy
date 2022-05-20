import chatserver.Client;
import chatserver.ClientChannel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServerApplication {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        ClientChannel clientChannel;

        try {
            serverSocket = new ServerSocket(8888);
            clientChannel = new ClientChannel();
            while (true) {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket, "channel01", "Ch1Room1");
                new Thread(client).start();
                System.out.println("[서버] : " + client + "이 접속했습니다.");
                clientChannel.addClientToRoom(client, "channel01", "Ch1Room1");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
