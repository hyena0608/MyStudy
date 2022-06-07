package clientserver.socket;

import clientserver.entity.command.onetoone.OneToOneChatting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static clientserver.socket.SocketType.ONETOONE_SOCKET;

public class OneToOneSocket implements Runnable, clientserver.socket.Socket {

    public static final String condition = String.valueOf(ONETOONE_SOCKET);
    private static volatile OneToOneSocket instance;
    private static Socket socket = null;
    private static DataInputStream in = null;
    private static DataOutputStream out = null;


    public OneToOneSocket() {
    }

    public OneToOneSocket(int port) {
        try {
            OneToOneSocket.socket = new Socket("localhost", port);
            OneToOneSocket.in = new DataInputStream(socket.getInputStream());
            OneToOneSocket.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static OneToOneSocket getInstance() {
        if (instance == null) {
            synchronized (OneToOneSocket.class) {
                if (instance == null) {
                    instance = new OneToOneSocket();
                }
            }
        }
        return instance;
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
