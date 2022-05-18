package chatserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientManager extends Thread {
    private List<Client> waitingRoom;
    private Thread waitingRoomThread;

    public ClientManager() {

        waitingRoom = Collections.synchronizedList(new ArrayList<>());
        waitingRoomThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    handleWaitingRoom();
                }
            }
        };
        waitingRoomThread.start();
    }


    public void addClient(Client client) {
        this.waitingRoom.add(client);
        System.out.println("[" + client.toString() + "] 님이 " + waitingRoom.getClass() + " 번 대기실에 접근합니다.");
        System.out.println("[서버] : 현재 " + waitingRoom.size() + "명이 대기실에 있습니다.");
        System.out.println("현재 대기실 리스트 : ");
        for (Client client1 : waitingRoom) {
            System.out.println(client1 + " ::: " + client1.getSocket());
        }
    }

    private void handleWaitingRoom() {
        System.out.println("handle");


        // TODO : receive와 broadcast 분리..
        synchronized (waitingRoom) {
            for (Client client : waitingRoom) {
                System.out.println("전해질 클라이언트 = " + client.toString());
                String message = client.receiveMessage();
                if (message != null && message.length() > 0) {
                    broadcastWaitingRoom(client, message);
                }
            }
        }
    }

    private synchronized void broadcastWaitingRoom(Client messageOwner, String message) {
        for (Client client : waitingRoom) {
            if (client != messageOwner)
                client.sendMessage(message);
        }
    }

}
