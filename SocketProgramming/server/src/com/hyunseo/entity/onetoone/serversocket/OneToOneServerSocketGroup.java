package com.hyunseo.entity.onetoone.serversocket;

import com.hyunseo.socket.onetoone.OneToOneServerSocket;

import java.util.*;

public class OneToOneServerSocketGroup {

    private static Map<Integer, OneToOneServerSocket> oneToOneServerSocketMap = new HashMap<>();
    private static Queue<Integer> portQueue = new LinkedList<>();

    static {
        for (int port = 10001; port <= 20000; port++)
            portQueue.offer(port);
    }

    public static Map<Integer, OneToOneServerSocket> getOneToOneServerSocketMap() {
        return oneToOneServerSocketMap;
    }

    public static Queue<Integer> getPortQueue() {
        return portQueue;
    }

    public static void addOneToOneServerSocket(OneToOneServerSocket oneToOneServerSocket) {
        oneToOneServerSocketMap.put(oneToOneServerSocket.getPort(), oneToOneServerSocket);
    }
}
