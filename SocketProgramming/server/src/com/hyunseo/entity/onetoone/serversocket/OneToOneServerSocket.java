package com.hyunseo.entity.onetoone.serversocket;

import java.net.ServerSocket;
import java.util.*;

public class OneToOneServerSocket {

    private static Map<Integer, ServerSocket> oneToOneServerSocketMap = new HashMap<>();
    private static Queue<Integer> portQueue = new LinkedList<>();

    static {
        for (int port = 10001; port <= 20000; port++)
            portQueue.offer(port);
    }

    public static Map<Integer, ServerSocket> getOneToOneServerSocketMap() {
        return oneToOneServerSocketMap;
    }

    public static Queue<Integer> getPortQueue() {
        return portQueue;
    }
}
