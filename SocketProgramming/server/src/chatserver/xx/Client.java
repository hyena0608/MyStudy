package chatserver.xx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    public DataOutputStream out;
    public DataInputStream in;

    public Client(Socket socket) {
        this.socket = socket;
        try {
            this.out = new DataOutputStream(socket.getOutputStream());
            this.in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToClientServer(String messageFromClientServer) {
        try {
            System.out.println("메시지를 전달합니다" + messageFromClientServer);
            this.out.writeUTF(messageFromClientServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getMessageFromClientServer() {
        String message = null;
        try {
            message = this.in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            getMessageFromClientServer();
        }
    }
}
