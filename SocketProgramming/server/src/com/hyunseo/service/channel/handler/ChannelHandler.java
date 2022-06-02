package com.hyunseo.service.channel.handler;

import com.hyunseo.entity.channel.Channel;
import com.hyunseo.entity.channel.Room;
import com.hyunseo.socket.UserSocket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChannelHandler {

    private static Map<String, Map<String, Room>> channelMap = new Channel().getChannelMap();

    public static void addUser(UserSocket userSocket) {
        System.out.println("userSocket.getUser().getUsername() = " + userSocket.getUser().getUsername());
        System.out.println("userSocket.getUser().getChannelTitle() = " + userSocket.getUser().getChannelTitle());
        System.out.println("userSocket.getUser().getRoomTitle() = " + userSocket.getUser().getRoomTitle());
        channelMap.get(userSocket.getUser().getChannelTitle())
                .get(userSocket.getUser().getRoomTitle())
                .getUserSocketList()
                .add(userSocket);

    }

    public static void init() {
        // TODO : 한글로 하면 안되네..
        Map<String, Room> roomMap1 = new HashMap<>();
        roomMap1.put("abc", new Room());
        roomMap1.put("2번방", new Room());
        roomMap1.put("3번방", new Room());
        Collections.synchronizedMap(roomMap1);
        channelMap.put("abc", roomMap1);

        Map<String, Room> roomMap2 = new HashMap<>();
        roomMap2.put("1번방", new Room());
        roomMap2.put("2번방", new Room());
        roomMap2.put("3번방", new Room());
        Collections.synchronizedMap(roomMap2);
        channelMap.put("1번채널", roomMap2);

        Map<String, Room> roomMap3 = new HashMap<>();
        roomMap3.put("1번방", new Room());
        roomMap3.put("2번방", new Room());
        roomMap3.put("3번방", new Room());
        Collections.synchronizedMap(roomMap3);
        channelMap.put("1번채널", roomMap3);

        Collections.synchronizedMap(channelMap);
    }

    public static Map<String, Map<String, Room>> getChannelMap() {
        return channelMap;
    }
}