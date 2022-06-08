package clientserver.entity.command.factory;

import clientserver.socket.OneToOneUserSocket;
import clientserver.socket.Socket;
import clientserver.socket.UserSocket;

public class SocketFactory {
    private Socket socket = null;

    public Socket createSocket(String type) {

        if (type.equals(UserSocket.condition)) {
            socket = UserSocket.getInstance();
        } else if (type.equals(OneToOneUserSocket.condition)) {
            socket = OneToOneUserSocket.getInstance();
        }

        return socket;
    }
}
