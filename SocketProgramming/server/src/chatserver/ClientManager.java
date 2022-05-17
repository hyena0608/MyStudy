package chatserver;

import java.net.Socket;
import java.util.*;

public class ClientManager implements Runnable {
    private List<Client> waitingRoom;


    public void addClient(Socket socket) {
        waitingRoom.add(new Client(socket));
        System.out.println("[" + socket.getInetAddress() + "] 님이 " + waitingRoom.getClass() + " 번 대기실에 접근합니다.");
    }

    public void receiveMessage() {
        for (Client client : waitingRoom) {
            client.receiveMessage();
        }
    }

    public void sendReceivedMessageToAll(String message) {
        for (Client client : waitingRoom) {
            client.sendMessage(message);
        }
    }

    @Override
    public void run() {
        waitingRoom = new ArrayList<>();
        Collections.synchronizedList(waitingRoom);
        receiveMessage();
    }
}
