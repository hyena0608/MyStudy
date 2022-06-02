package com.hyunseo.socket;

import com.hyunseo.entity.command.factory.CommandFactory;
import com.hyunseo.entity.user.User;

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

    public UserSocket(Socket socket, User user) {
        try {
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.user = user;
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
        System.out.println("[서버] : " + messageJson + " 를 전달합니다.");
        commandFactory.createCommand(messageJson).send(messageJson);
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            tossMessageJson(receive());
        }
    }
}