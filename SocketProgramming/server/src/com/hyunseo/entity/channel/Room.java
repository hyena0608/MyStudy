package com.hyunseo.entity.channel;

import com.hyunseo.socket.user.UserSocket;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private List<UserSocket> userSocketList = new ArrayList<>();

    public List<UserSocket> getUserSocketList() {
        return userSocketList;
    }
}
