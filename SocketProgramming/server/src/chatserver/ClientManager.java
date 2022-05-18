package chatserver;

import java.net.Socket;
import java.util.*;

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
        waitingRoom.add(client);
        System.out.println("[" + client.toString() + "] 님이 " + waitingRoom.getClass() + " 번 대기실에 접근합니다.");
        System.out.println("[서버] : 현재 " + waitingRoom.size() + "명이 대기실에 있습니다.");
    }

    private void handleWaitingRoom() {
        for (Client client : waitingRoom) {
            String message = client.receiveMessage();
            if (message != null && message.length() > 0) {
                broadcastWaitingRoom(client, message);
            }
        }
    }

    private void broadcastWaitingRoom(Client messageOwner, String message) {
        for (Client client : waitingRoom) {
            if (client != messageOwner)
                client.sendMessage(message);
        }
    }

//
//    @Override
//    public void run() {
//        waitingRoom = Collections.synchronizedList(new ArrayList<>());
//        Thread waitingRoomThread = new Thread() {
//            @Override
//            public void run() {
//                while (true) {
//                    handleWaitingRoom();
//                }
//            }
//        };
//        waitingRoomThread.start();
//    }
}
