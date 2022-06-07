package clientserver.entity.command.factory;

import clientserver.socket.OneToOneSocket;
import clientserver.socket.Socket;
import clientserver.socket.UserSocket;

public class SocketFactory {
    private Socket socket = null;

    public Socket createSocket(String type) {

        if (type.equals(UserSocket.condition)) {
            socket = new UserSocket();
        } else if (type.equals(OneToOneSocket.condition)) {
            socket = new OneToOneSocket();
        }

        return socket;
    }
}
