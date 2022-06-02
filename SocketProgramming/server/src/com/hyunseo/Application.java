package com.hyunseo;

import com.hyunseo.socket.main.MainSocket;

import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        try {
            new MainSocket().init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
