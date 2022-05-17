package clientserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientSender implements Runnable {

    Socket socket;
    DataOutputStream out;

    public ClientSender(Socket socket) {
        this.socket = socket;
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (out != null) {
            try {
                out.writeUTF(bufferedReader.readLine());
                System.out.println("메시지를 전송했습니다.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        sendMessage();
    }
}
