package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static chatserver.ClientChannel.channelMap;


public class ClientChannel extends Thread {
    static HashMap<String, HashMap<String, List<Client>>> channelMap;

    public ClientChannel() {
        channelMap = new HashMap<>();
        Collections.synchronizedMap(channelMap);

        HashMap<String, List<Client>> Ch1Room1 = new HashMap<String, List<Client>>();
        Ch1Room1.put("Ch1Room1", new ArrayList<>());
        Collections.synchronizedMap(Ch1Room1);

        HashMap<String, List<Client>> Ch2Room1 = new HashMap<String, List<Client>>();
        Ch1Room1.put("Ch2Room1", new ArrayList<>());
        Collections.synchronizedMap(Ch2Room1);

        HashMap<String, List<Client>> Ch3Room1 = new HashMap<String, List<Client>>();
        Ch1Room1.put("Ch3Room1", new ArrayList<>());
        Collections.synchronizedMap(Ch3Room1);

        channelMap.put("channel01", Ch1Room1);
        channelMap.put("channel02", Ch2Room1);
        channelMap.put("channel03", Ch3Room1);


    }

    public void addClientToRoom(Client client, String channel, String room) {
        channelMap.get(channel).get(room).add(client);

        System.out.println("더함");
        System.out.println("현재 방인원 목록 : ");
        for (HashMap<String, List<Client>> value : channelMap.values()) {
            System.out.println(value.get(room));
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket;
        ClientChannel clientChannel;

        try {
            serverSocket = new ServerSocket(8888);
            clientChannel = new ClientChannel();
//            clientChannel.start();
            while (true) {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket, "channel01", "Ch1Room1");
                new Thread(client).start();
                System.out.println("[서버] : " + client + "이 접속했습니다.");
                clientChannel.addClientToRoom(client, "channel01", "Ch1Room1");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Client implements Runnable {
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
        for (Client eachClient : channelMap.get(client.channelName).get(client.roomName)) {
            try {
                if (!eachClient.equals(client)) {
                    System.out.println(client.channelName + "채널에 " + client.roomName + "방에 전달함");
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
