package com.hyunseo.entity.channel;

import com.hyunseo.socket.UserSocket;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private static Map<String, Map<String, UserSocket>> roomMap = new HashMap<>();

    public static Map<String, Map<String, UserSocket>> getRoomMap() {
        return roomMap;
    }

}
