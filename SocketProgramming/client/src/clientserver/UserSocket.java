package clientserver;

import clientserver.entity.command.user.UserSetting;
import clientserver.entity.user.User;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;

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
            user = getUserAfterSetting();
            sendUserSettingMessage(UserSetting.condition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User getUserAfterSetting() {
        User user = new User();
        user.init();
        return user;
    }

    private void sendUserSettingMessage(String condition) {
        new SocketMessageHandlerImpl().sendUserSetting(condition);
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
