package com.hyunseo.entity.onetoone;

import com.hyunseo.socket.onetoone.OneToOneUserSocket;

import java.util.ArrayList;
import java.util.List;

public class OneToOneUserSocketMate {

    private List<OneToOneUserSocket> oneToOneUserSocketList = new ArrayList<>();

    public List<OneToOneUserSocket> getOneToOneUserSocketList() {
        return oneToOneUserSocketList;
    }
}
