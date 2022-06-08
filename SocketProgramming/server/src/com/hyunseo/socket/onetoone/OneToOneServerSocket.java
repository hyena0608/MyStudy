package com.hyunseo.socket.onetoone;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
                Socket oneToOneUserSocket = serverSocket.accept();
                new Thread(new OneToOneUserSocket(oneToOneUserSocket)).start();
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
