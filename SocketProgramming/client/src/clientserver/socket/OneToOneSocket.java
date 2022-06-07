package clientserver.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class OneToOneSocket implements Runnable {

    static private Socket socket = null;
    static private DataInputStream in = null;
    static private DataOutputStream out = null;

    public OneToOneSocket(int port) {
        try {
            OneToOneSocket.socket = new Socket("localhost", port);
            OneToOneSocket.in = new DataInputStream(socket.getInputStream());
            OneToOneSocket.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
