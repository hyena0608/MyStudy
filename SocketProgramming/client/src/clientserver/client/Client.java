package clientserver.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static Socket socket;
    private static DataOutputStream out;
    private static DataInputStream in;
    private static ClientInfo clientInfo;

    public Client(Socket socket, ClientInfo clientInfo) {
        try {
            Client.socket = socket;
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            Client.clientInfo = clientInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Socket getSocket() {
        return socket;
    }

    public static DataOutputStream getOut() {
        return out;
    }

    public static DataInputStream getIn() {
        return in;
    }

    public static ClientInfo getClientInfo() {
        return clientInfo;
    }

}