package com.hyunseo.socket.onetoone;

import java.util.LinkedList;
import java.util.Queue;

public class Port {
    static Queue<Integer> portQ = new LinkedList<>();

    static {
        for (int port = 10000; port <= 20000; port++) {
            portQ.offer(port);
        }
    }
}
