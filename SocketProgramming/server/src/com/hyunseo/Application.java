package com.hyunseo;

import com.hyunseo.socket.main.MainServerSocket;

import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        try {
            new MainServerSocket().init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
