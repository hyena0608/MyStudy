package com.hyunseo.socket.onetoone;

import java.io.IOException;
import java.net.ServerSocket;

public class OneToOneServerSocket implements Runnable {

    private ServerSocket serverSocket;
    private int port;

    public void startServerSocket() {
        port = Port.portQ.poll();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // TODO : 메서드 내에서 run() 호출해서 꺼져버림. 따로 행동하게 만들어야함.
            while (true) {
                System.out.println("모집중");
                serverSocket.accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }
}
