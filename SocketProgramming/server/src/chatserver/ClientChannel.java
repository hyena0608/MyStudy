package chatserver;

import java.util.*;

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
}