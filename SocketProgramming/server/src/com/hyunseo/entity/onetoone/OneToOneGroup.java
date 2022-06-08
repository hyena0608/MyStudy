package com.hyunseo.entity.onetoone;

import com.hyunseo.socket.onetoone.OneToOneServerSocket;

import java.util.*;

public class OneToOneGroup {

    private static Map<Integer, OneToOneServerSocket> oneToOneServerSocketMap = new HashMap<>();
    private static Map<Integer, OneToOneUserSocketMate> oneToOneUserSocketMateMap = new HashMap<>();

    public static Map<Integer, OneToOneServerSocket> getOneToOneServerSocketMap() {
        return oneToOneServerSocketMap;
    }

    public static Map<Integer, OneToOneUserSocketMate> getOneToOneUserSocketMateMap() {
        return oneToOneUserSocketMateMap;
    }
}
