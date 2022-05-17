package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(Socket socket) {
        this.socket = socket;
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {}
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveMessage() {
        try {
            while (in != null) {
                System.out.println(in.readUTF());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public void run() {
        receiveMessage();
    }
}
