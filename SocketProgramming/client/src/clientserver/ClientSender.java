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
    }

    private void setDataOut() {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("[클라 서버] : ClientSender가 메시지 전송 준비가 됐습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage() {
        setDataOut();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (out != null) {
            try {
                out.writeUTF(bufferedReader.readLine());
                System.out.println("[자신] : 메시지를 전송했습니다.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        System.out.println("[클라 서버] : ClientSender 실행");
        sendMessage();
    }
}
