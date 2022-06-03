package com.hyunseo.service.onetoone.serversocket.handler;

import com.hyunseo.entity.onetoone.serversocket.OneToOneServerSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class OneToOneServerSocketHandler implements Runnable {

    public int createOneToOneServerSocket() {
        int port = 0;
        try {
            port = OneToOneServerSocket
                    .getPortQueue()
                    .poll();

            if (isPortEmpty(port)) {
                throw new NullPointerException("귓속말 전용 포트가 모두 사용되어지고 있습니다.");
            }

            OneToOneServerSocket
                    .getOneToOneServerSocketMap()
                    .put(port, new ServerSocket(port));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }

    private void acceptServerSocket(int port) {
        try {
            OneToOneServerSocket
                    .getOneToOneServerSocketMap()
                    .get(port)
                    .accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isPortEmpty(Integer port) {
        if (port == null) {
            return true;
        } else {
            return false;
        }
    }

    public void closeOneToOneServerSocket(int port) {
        try {
            OneToOneServerSocket
                    .getOneToOneServerSocketMap()
                    .get(port)
                    .close();

            OneToOneServerSocket
                    .getPortQueue()
                    .add(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
//        acceptServerSocket()
    }
}
