package clientserver.socket;

import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.entity.user.User;
import clientserver.service.socket.parser.SocketMessageParserImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static clientserver.socket.SocketType.ONETOONE_SOCKET;

public class OneToOneUserSocket implements Runnable, clientserver.socket.Socket {

    public static final String condition = String.valueOf(ONETOONE_SOCKET);
    private static volatile OneToOneUserSocket instance;
    private static Socket socket = null;
    private static DataInputStream in = null;
    private static DataOutputStream out = null;


    public OneToOneUserSocket() {
    }

    public static void init(int port) {
        try {
            OneToOneUserSocket.socket = new Socket("localhost", port);
            OneToOneUserSocket.in = new DataInputStream(socket.getInputStream());
            OneToOneUserSocket.out = new DataOutputStream(socket.getOutputStream());
            sendOneToOneUserSocketSettingMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static OneToOneUserSocket getInstance() {
        if (instance == null) {
            synchronized (OneToOneUserSocket.class) {
                if (instance == null) {
                    instance = new OneToOneUserSocket();
                }
            }
        }
        return instance;
    }

    private static void sendOneToOneUserSocketSettingMessage() throws IOException {
        MessageObject messageObject
                = new MessageObjectBuilder()
                        .setUser(UserSocket.getUser())
                        .setMessageType(String.valueOf(ONETOONE_SOCKET))
                        .build();
        String messageJson = new SocketMessageParserImpl().toJson(messageObject);
        out.writeUTF(messageJson);
    }

    @Override
    public void run() {

    }

    public static Socket getSocket() {
        return socket;
    }

    public static DataInputStream getIn() {
        return in;
    }

    public static DataOutputStream getOut() {
        return out;
    }

    @Override
    public DataOutputStream takeOut() {
        return out;
    }
}
