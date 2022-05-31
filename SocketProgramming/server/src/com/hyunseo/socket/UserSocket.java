package com.hyunseo.socket;

import com.hyunseo.entity.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class UserSocket extends Thread {

    private User user;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public UserSocket(Socket socket, User user) {
        try {
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.user = user;
            System.out.println("user.getChannelTitle() = " + user.getChannelTitle());
            System.out.println("user.getRoomTitle() = " + user.getRoomTitle());
            System.out.println("user.getUsername() = " + user.getUsername());
            System.out.println("user.getUserCondition() = " + user.getUserCondition());
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
}