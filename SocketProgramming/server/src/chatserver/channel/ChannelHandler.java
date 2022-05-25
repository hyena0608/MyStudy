package chatserver.channel;

import chatserver.client.ClientInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ChannelHandler extends Thread {
    private static HashMap<String, HashMap<String, List<ClientInfo>>> channelMap;

    public static HashMap<String, HashMap<String, List<ClientInfo>>> getChannelMap() {
        return channelMap;
    }

    public ChannelHandler() {
        channelMap = new HashMap<>();
        Collections.synchronizedMap(channelMap);

        HashMap<String, List<ClientInfo>> Ch1Room1 = new HashMap<String, List<ClientInfo>>();
        Ch1Room1.put("Ch1Room1", new ArrayList<>());
        Collections.synchronizedMap(Ch1Room1);

        HashMap<String, List<ClientInfo>> Ch2Room1 = new HashMap<String, List<ClientInfo>>();
        Ch2Room1.put("Ch2Room1", new ArrayList<>());
        Collections.synchronizedMap(Ch2Room1);

        HashMap<String, List<ClientInfo>> Ch3Room1 = new HashMap<String, List<ClientInfo>>();
        Ch3Room1.put("Ch3Room1", new ArrayList<>());
        Collections.synchronizedMap(Ch3Room1);

        channelMap.put("channel01", Ch1Room1);
        channelMap.put("channel02", Ch2Room1);
        channelMap.put("channel03", Ch3Room1);
    }

    public void addClientToRoom(ClientInfo clientInfo) {
        channelMap
                .get(clientInfo.getChannel())
                .get(clientInfo.getRoom())
                .add(clientInfo);
    }

//    public void removeClientFromRoom(ClientHandler clientHandler, ClientInfo clientInfo) {
//        System.out.println("방에서 회원 지우기");
//        channelMap
//                .get(clientInfo.getChannel())
//                .get(clientInfo.getRoom())
//                .remove(clientHandler);
//    }


//    public String printChannelAndRoomCurrentSize() {
//        StringBuffer stringBuffer = new StringBuffer();
//        String currentChannelName = "";
//        String currentRoomName = "";
//        int currentRoomCount = 0;
//        stringBuffer.append(MessageType_1.CURRENTSTATUS.getType()).append("|");
//        for (String channelKey : channelMap.keySet()) {
//            currentChannelName = channelKey;
//            stringBuffer.append("채널 : [").append(currentChannelName).append("] ----------------------------------")
//                        .append("\n");
//
//            for (String roomKey : channelMap.get(channelKey).keySet()) {
//                currentRoomName = roomKey;
//                currentRoomCount = channelMap.get(channelKey).get(roomKey).size();
//                System.out.println("방 : [" + currentRoomName + "] : " + currentRoomCount + "명");
//                stringBuffer.append("방 : [").append(currentRoomName).append("] : ").append(currentRoomCount).append("명");
//            }
//        }
//        return stringBuffer.toString();
//    }

}