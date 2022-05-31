package com.hyunseo.service.socket;

import com.hyunseo.entity.user.User;

import java.net.Socket;

public class UserSocket {

    private User user;
    private Socket socket;

    public UserSocket(Socket socket) {
        this.socket = socket;
    }

    public User getUser() {
        return user;
    }

}