package com.hyunseo.socket;

import com.google.gson.Gson;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.service.channel.handler.ChannelHandler;
import com.hyunseo.service.user.handler.UserSocketMessageHandler;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainSocket {

    public void init() throws IOException {
        ChannelHandler.init();
        ServerSocket serverSocket = new ServerSocket(8888);

        while (true) {
            Socket socket = serverSocket.accept();

            String messageJson = new DataInputStream(socket.getInputStream()).readUTF();
            MessageObject messageObject = new Gson().fromJson(messageJson, MessageObject.class);

            UserSocket userSocket = new UserSocket(socket, messageObject.getUser());
            ChannelHandler.addUser(userSocket);
            new Thread(userSocket).start();
        }
    }
}
