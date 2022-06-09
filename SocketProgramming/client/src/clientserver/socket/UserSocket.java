package clientserver.socket;

import clientserver.entity.command.user.UserSetting;
import clientserver.entity.user.User;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class UserSocket {

    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
    private static User user;

    public void init() {
        try {
            socket = new Socket("localhost", 8888);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            printChannelInfo();
            user = getUserAfterSetting();
            sendUserSettingMessage(UserSetting.condition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printChannelInfo() {
        try {
            System.out.println(getChannelInfo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getChannelInfo() throws IOException {
            return in.readUTF();
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
