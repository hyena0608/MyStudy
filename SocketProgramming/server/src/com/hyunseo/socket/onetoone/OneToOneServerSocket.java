package com.hyunseo.socket.onetoone;

import java.io.IOException;
import java.net.ServerSocket;

public class OneToOneServerSocket implements Runnable {

    private ServerSocket serverSocket;
    private int port;

    public void startServerSocket() {
        try {
            port = Port.portQ.poll();
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println(" 귓속말 서버 소켓 시작 = ");
            while (true) {
                System.out.println("모집중");
                serverSocket.accept();
                System.out.println("모집중2");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
