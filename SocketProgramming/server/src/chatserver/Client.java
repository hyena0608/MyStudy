package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static chatserver.MessageParser.parseMessage;

public class Client implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private final ClientInfo clientInfo;

    public Client(Socket socket) {
        this.socket = socket;
        this.clientInfo = getClientInfo(socket);
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
            System.out.println("[서버] : '" + messageFromClientServer + "' 라는 메시지를 받았습니다.");
            broadcastMessage(messageFromClientServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String messageFromClientServer) {
        for (Client eachClient : ClientChannel
                                        .channelMap
                                        .get(clientInfo.channel)
                                        .get(clientInfo.room)) {
            try {
                if (!eachClient.equals(this)) {
                    System.out.println("[" + clientInfo.channel + "] 채널에 "
                            + "[" + clientInfo.room + "] 방에 '"
                            + messageFromClientServer + "'라는 메시지를 전달했습니다.");
                    eachClient.out.writeUTF(messageFromClientServer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ClientInfo getClientInfo(Socket socket) {
        ClientInfo clientInfo = null;
        try {
            String ClientInfoString = new DataInputStream(socket.getInputStream()).readUTF();
            String[] clientInfoArr = parseMessage(ClientInfoString);
            clientInfo = new ClientInfo(
                    clientInfoArr[0],
                    clientInfoArr[1],
                    clientInfoArr[2]
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientInfo;
    }

    public ClientInfo getClientInfo() {
        return this.clientInfo;
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            getMessageFromClientServer();
        }
    }
}
