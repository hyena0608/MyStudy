package com.hyunseo.service.onetoone;

import com.hyunseo.entity.onetoone.OneToOneGroup;
import com.hyunseo.entity.onetoone.OneToOneUserSocketMate;
import com.hyunseo.socket.onetoone.OneToOneServerSocket;
import com.hyunseo.socket.onetoone.OneToOneUserSocket;

public class OneToOneGroupHandler {

    public static void putOneToOneServerSocket(OneToOneServerSocket oneToOneServerSocket, int port) {
        OneToOneGroup
                .getOneToOneServerSocketMap()
                .put(port, oneToOneServerSocket);
    }

    public static void putOneToOneUserSocketMap(OneToOneUserSocket oneToOneUserSocket, int port) {

        if (!OneToOneGroup
                .getOneToOneUserSocketMateMap()
                .containsKey(port)) {
            OneToOneGroup
                    .getOneToOneUserSocketMateMap()
                    .put(port, new OneToOneUserSocketMate());
        }

        if (OneToOneGroup
                .getOneToOneUserSocketMateMap()
                .containsKey(port)) {
            OneToOneGroup
                    .getOneToOneUserSocketMateMap()
                    .get(port)
                    .getOneToOneUserSocketList()
                    .add(oneToOneUserSocket);
        }
    }
}
