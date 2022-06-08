package com.hyunseo.socket.onetoone;

import com.hyunseo.entity.command.factory.CommandFactory;
import com.hyunseo.entity.message.MessageObject;
import com.hyunseo.entity.user.User;
import com.hyunseo.service.onetoone.OneToOneGroupHandler;
import com.hyunseo.service.user.parser.UserSocketMessageParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class OneToOneUserSocket implements Runnable {

    private User user;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private CommandFactory commandFactory = new CommandFactory();

    public OneToOneUserSocket(Socket socket) {
        try {
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.user = getUserFromClientOneToOneSocket();
            OneToOneGroupHandler.putOneToOneUserSocketMap(this, user.getPort());
            System.out.println("user.getUsername() = " + user.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User getUserFromClientOneToOneSocket() throws IOException {
        String messageJson = new DataInputStream(socket.getInputStream()).readUTF();
        MessageObject messageObject = UserSocketMessageParser.toObject(messageJson);

        if (!messageObject.getMessageType().equals("ONETOONE_SOCKET")) {
           return null;
        }
        return messageObject.getUser();
    }

    private String receive() throws IOException {
        return in.readUTF();
    }

    private void tossMessageJson(String messageJson) {
        System.out.println("귓속말 넘겨버리기 " + messageJson);
        commandFactory.createCommand(messageJson).send(messageJson);
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                tossMessageJson(receive());
            }
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
}
