package com.hyunseo.service.channel.handler;

import com.hyunseo.entity.channel.Channel;
import com.hyunseo.entity.channel.Room;
import com.hyunseo.socket.user.UserSocket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.hyunseo.entity.channel.ChannelRoom.*;

public class ChannelHandler {


    public static void init() {
        // TODO : 한글로 하면 안되네..
        Map<String, Room> roomMap1 = new HashMap<>();
        roomMap1.put(CHANNEL1.roomOne, new Room());
        roomMap1.put(CHANNEL1.roomTwo, new Room());
        roomMap1.put(CHANNEL1.roomThree, new Room());
        Collections.synchronizedMap(roomMap1);
        Channel.getChannelMap().put(CHANNEL1.channel, roomMap1);

        Map<String, Room> roomMap2 = new HashMap<>();
        roomMap2.put(CHANNEL2.roomOne, new Room());
        roomMap2.put(CHANNEL2.roomTwo, new Room());
        roomMap2.put(CHANNEL2.roomThree, new Room());
        Collections.synchronizedMap(roomMap2);
        Channel.getChannelMap().put(CHANNEL2.channel, roomMap2);

        Map<String, Room> roomMap3 = new HashMap<>();
        roomMap3.put(CHANNEL3.roomOne, new Room());
        roomMap3.put(CHANNEL3.roomTwo, new Room());
        roomMap3.put(CHANNEL3.roomThree, new Room());
        Collections.synchronizedMap(roomMap3);
        Channel.getChannelMap().put(CHANNEL3.channel, roomMap3);

        Collections.synchronizedMap(Channel.getChannelMap());
    }

    public static void addUser(UserSocket userSocket) {
        Channel.getChannelMap()
                .get(userSocket.getUser().getChannelTitle())
                .get(userSocket.getUser().getRoomTitle())
                .getUserSocketList()
                .add(userSocket);
    }

    public static void removeMyUserSocket(UserSocket userSocket) {
        Channel.getChannelMap()
                .get(userSocket.getUser().getChannelTitle())
                .get(userSocket.getUser().getRoomTitle())
                .getUserSocketList()
                .remove(userSocket);
    }

    public static Map<String, Map<String, Room>> getChannelMap() {
        return Channel.getChannelMap();
    }
}
