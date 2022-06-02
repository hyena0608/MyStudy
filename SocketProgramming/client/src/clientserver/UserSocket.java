package clientserver;

import clientserver.entity.command.UserSetting;
import clientserver.entity.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class UserSocket {
    static private Socket socket;
    static private DataInputStream in;
    static private DataOutputStream out;
    static private User user;

    public void init() {
        try {
            socket = new Socket("localhost", 8888);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            User member = new User();
            member.init();
            user = member;
            UserSetting.getInstance().changeMySetting();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataInputStream getIn() {
        return in;
    }

    public static DataOutputStream getOut() {
        return out;
    }

    public static User getUser() {
        return user;
    }

    public static Socket getSocket() {
        return socket;
    }
}
