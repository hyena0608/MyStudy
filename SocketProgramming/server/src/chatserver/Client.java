package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
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
            System.out.println("[서버] : '" + message + "' 라는 메시지를 전송합니다.");
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String receiveMessage() {
        String message = null;
        try {
            message = in.readUTF();
            System.out.println("[서버] : '" + message + "' 라는 메시지를 입력 받았습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return message;
    }
}
