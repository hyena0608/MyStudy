package clientserver;

import clientserver.client.Client;
import clientserver.client.ClientInfo;
import clientserver.client.ClientReceiver;
import clientserver.client.ClientSender;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class BaseClientServer {
    private Socket socket;
    private Client client;
    private ClientInfo clientInfo;
    private ClientSender clientSender;
    private ClientReceiver clientReceiver;

    Scanner sc = new Scanner(System.in);

    public void clientServerStart() {
        try {
            socket = new Socket("localhost", 8888);

            clientInfo = new ClientInfo();
            sendClientJsonToServer(clientInfo.clientInfoToJson());
            client = new Client(socket, clientInfo);
            clientSender = new ClientSender();
            clientReceiver = new ClientReceiver();

            System.out.println("[" + socket.getInetAddress() + "] 으로 접속하였습니다");

            new Thread(clientSender).start();
            new Thread(clientReceiver).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendClientJsonToServer(String clientJson) throws IOException {
        new DataOutputStream(socket.getOutputStream()).writeUTF(clientJson);
    }
}
