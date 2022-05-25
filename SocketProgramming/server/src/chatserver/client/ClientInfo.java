package chatserver.client;

import chatserver.message.MessageForm;
import chatserver.message.MessageType;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientInfo extends Thread{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;
    private String channel;
    private String room;

    public ClientInfo(Socket socket) {
        try {
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            MessageForm clientObject = readClient(socket);
            this.username = clientObject.getSender();
            this.channel = clientObject.getChannelNumber();
            this.room = clientObject.getRoomNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public String getUsername() {
        return username;
    }

    public String getChannel() {
        return channel;
    }

    public String getRoom() {
        return room;
    }

    private MessageForm readClient(Socket socket) throws IOException {
        MessageForm clientObject = new Gson().fromJson(in.readUTF(), MessageForm.class);
        if (clientObject.getMessageType().equals(String.valueOf(MessageType.CLIENTINFO))) {
            return clientObject;
        } else {
            throw new IOException("로그인 회원 정보 메시지가 아닙니다.");
        }
    }
}
