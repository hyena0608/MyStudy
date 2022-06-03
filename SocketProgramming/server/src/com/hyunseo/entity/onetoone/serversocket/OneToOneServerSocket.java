package com.hyunseo.entity.onetoone.serversocket;

import com.hyunseo.entity.onetoone.OneToOne;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OneToOneServerSocket {

    private static List<OneToOne> oneToOneList = new ArrayList<>();
    private static List<ServerSocket> serverSocketList = new ArrayList<>();
    private static Queue<Integer> portQueue = new LinkedList<>();

    static {
        for (int port = 10001; port <= 20000; port++)
            portQueue.add(port);
    }

    public static List<ServerSocket> getServerSocketList() {
        return serverSocketList;
    }

    public static Queue<Integer> getPortQueue() {
        return portQueue;
    }
}
