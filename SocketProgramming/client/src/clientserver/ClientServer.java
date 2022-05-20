package clientserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientServer {
    private ClientSender clientSender;
    private ClientReceiver clientReceiver;
    private Thread clientSenderThread;
    private Thread clientReceiverThread;
    Scanner sc = new Scanner(System.in);

    public void clientServerStart() {
        try {
            Socket socket = new Socket("localhost", 8888);
            new DataOutputStream(socket.getOutputStream()).writeUTF(clientInfoString());

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

    private String clientInfoString() {
        return setClientUsername() + "|" + setClientChannel() + "|" + setClientRoom();
    }

    public String setClientUsername() {
        System.out.println("[서버] : 사용할 닉네임을 입력해주세요");
        return sc.nextLine();
    }

    public String setClientChannel() {
        System.out.println("[서버] : 입장할 채널을 입력해주세요");
        return sc.nextLine();
    }

    public String setClientRoom() {
        System.out.println("[서버] : 입장할 방을 입력해주세요");
        return sc.nextLine();
    }
}
