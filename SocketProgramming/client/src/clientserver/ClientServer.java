package clientserver;

import java.io.IOException;
import java.net.Socket;

public class ClientServer {
    private ClientSender clientSender;
    private ClientReceiver clientReceiver;
    private Thread clientSenderThread;
    private Thread clientReceiverThread;

    public void clientServerStart() {
        try {
            Socket socket = new Socket("localhost", 8888);


            clientSender = new ClientSender(socket);
            clientReceiver = new ClientReceiver(socket);
            clientSenderThread = new Thread(clientSender);
            clientReceiverThread = new Thread(clientReceiver);
            System.out.println("[" + socket.getInetAddress() + "] 으로 접속하였습니다");

            clientSenderThread.start();
            clientReceiverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
