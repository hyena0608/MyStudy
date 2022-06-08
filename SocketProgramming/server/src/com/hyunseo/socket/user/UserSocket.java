package com.hyunseo.socket.user;

import com.hyunseo.entity.command.factory.CommandFactory;
import com.hyunseo.entity.user.User;
import com.hyunseo.service.user.parser.UserSocketMessageParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class UserSocket implements Runnable {

    private User user;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private CommandFactory commandFactory = new CommandFactory();

    public UserSocket(Socket socket) {
        try {
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.user = getUserFromClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setUser(User user) {
        this.user = user;
    }



    private String receive() {
        String messageJson = null;
        try {
            messageJson = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageJson;
    }

    private void tossMessageJson(String messageJson) {
        System.out.println("tossMessageJson() : messageJson = " + messageJson);
        commandFactory.createCommand(messageJson).send(messageJson);
    }

    private User getUserFromClient() {
        User user = null;
        try {
            String messageJson = new DataInputStream(socket.getInputStream()).readUTF();
            user = UserSocketMessageParser
                    .toObject(messageJson)
                    .getUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            tossMessageJson(receive());
        }
    }
}