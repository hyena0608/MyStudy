package clientserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReceiver implements Runnable {
    private Socket socket;
    private DataInputStream in;

    public ClientReceiver(Socket socket) {
        this.socket = socket;
    }

    public void receiveMessage() {
        try {
            this.in = new DataInputStream(socket.getInputStream());

            while(this.in != null) {
                System.out.println(socket.getInetAddress() + " : " + in.readUTF());
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
