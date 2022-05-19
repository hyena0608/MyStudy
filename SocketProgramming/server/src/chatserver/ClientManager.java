package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientManager implements Runnable {
    private static List<ClientManager> clientManagers = new ArrayList<>();
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientManager(Socket socket) {
        try {
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            clientManagers.add(this);
        } catch (Exception e) {
        }
    }

    private void handlingWaitingRoom() {
        try {
            String message = this.in.readUTF();
            broadcast(message);
        } catch (IOException e) {
        }
    }

    private void broadcast(String message) {
        for (ClientManager clientManager : clientManagers) {
            if (!clientManager.equals(this)) {
                clientManager.sendMessage(clientManager, message);
            }
        }
    }

    private void sendMessage(ClientManager clientManager, String message) {
        try {
            clientManager.out.writeUTF(message);
        } catch (IOException e) {

        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            handlingWaitingRoom();
        }
    }
}