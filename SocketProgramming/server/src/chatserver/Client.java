package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    public final String channelName;
    public final String roomName;

    public Client(Socket socket, String channelName, String roomName) {
        this.socket = socket;
        this.channelName = channelName;
        this.roomName = roomName;
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getMessageFromClientServer() {
        try {
            String messageFromClientServer = this.in.readUTF();
            System.out.println("[서버] : " + messageFromClientServer + "받음");
            broadcastMessage(this, messageFromClientServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(Client client, String messageFromClientServer) {
        for (Client eachClient : ClientChannel.channelMap.get(client.channelName).get(client.roomName)) {
            try {
                if (!eachClient.equals(client)) {
                    System.out.println("[" + client.channelName + "] 채널에 " + "[" + client.roomName + "] 방에 전달함");
                    eachClient.out.writeUTF(messageFromClientServer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            getMessageFromClientServer();
        }
    }
}
