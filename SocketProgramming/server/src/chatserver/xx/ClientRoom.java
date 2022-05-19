package chatserver.xx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ClientRoom implements Runnable {

    private static List<Client> clientList;

    public ClientRoom() {
        clientList = Collections.synchronizedList(new ArrayList<>());
    }

    private void anyMessageFromClientServer() {
        Iterator<Client> iterator = clientList.iterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            iterator.remove();
            try {
                String s = client.in.readUTF();
                broadcastMessage(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        for (Client client : clientList) {
//            System.out.println("anyMessageFromClientServer");
//            String messageFromClientServer = client.getMessageFromClientServer();
//            if (messageFromClientServer != null)
//                broadcastMessage(messageFromClientServer);
//        }
    }


    public static void broadcastMessage(String messageFromClientServer) {
        for (Client client : clientList) {
            System.out.println("broadcastMessage()");
            try {
                client.out.writeUTF(messageFromClientServer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addClient(Client client) {
        clientList.add(client);

        System.out.println("현재 방 [" + this + "] 인원 목록 ::::::::::::");
        for (Client client1 : clientList) {
            System.out.println(client1);
        }
    }

    @Override
    public void run() {
        while (true) {
            anyMessageFromClientServer();
        }
    }
}
