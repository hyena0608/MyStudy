package com.hyunseo.socket.user;

import com.hyunseo.entity.channel.ChannelRoom;
import com.hyunseo.entity.command.factory.CommandFactory;
import com.hyunseo.entity.user.User;
import com.hyunseo.service.channel.handler.ChannelHandler;
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
            sendChannelInfo();
            this.user = getUserFromClient();
        } catch (IOException e) {
            closeSocket();
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

    private void sendChannelInfo() {
        try {
            this.out.writeUTF(ChannelRoom.info());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try {
            while (in != null) {
                tossMessageJson(receive());
            }
        } catch (IOException e) {
        } finally {
            closeSocket();
        }
    }

    private String receive() throws IOException {
        return in.readUTF();
    }

    private void tossMessageJson(String messageJson) {
        commandFactory.createCommand(messageJson).send(messageJson);
    }

    private void closeSocket() {
        try {
            in.close();
            out.close();
            socket.close();
            ChannelHandler.removeMyUserSocker(this);
        } catch (IOException e) {
            this.closeSocket();
        }
    }
}