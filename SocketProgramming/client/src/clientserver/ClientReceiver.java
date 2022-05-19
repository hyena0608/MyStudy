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

    private void setDataIn() {
        try {
            in = new DataInputStream(socket.getInputStream());
            System.out.println("[클라 서버] : ClientReceiver가 메시지를 받을 준비가 됐습니다.");
        } catch (IOException e) {}
    }

    public void receiveMessage() {
        try {
            setDataIn();
            while(this.in != null) {
                System.out.println("상대방 : " + in.readUTF());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        System.out.println("[클라 서버] : ClientReceiver 실행");
        receiveMessage();
    }
}
