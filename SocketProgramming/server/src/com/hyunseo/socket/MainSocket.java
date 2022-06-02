package com.hyunseo.socket;

import com.hyunseo.service.channel.handler.ChannelHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainSocket {

    public void init() throws IOException {
         ChannelHandler.init();
         ServerSocket serverSocket = new ServerSocket(8888);

        while (true) {
            Socket socket = serverSocket.accept();
            UserSocket userSocket = new UserSocket(socket);
            ChannelHandler.addUser(userSocket);
            new Thread(userSocket).start();
        }
    }
}
