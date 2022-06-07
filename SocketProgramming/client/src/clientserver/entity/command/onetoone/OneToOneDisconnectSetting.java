package clientserver.entity.command.onetoone;

import clientserver.socket.UserSocket;
import clientserver.entity.command.base.Setting;

import java.io.IOException;

import static clientserver.entity.command.onetoone.OneToOne.ONETOONE_END_SETTING;

public class OneToOneDisconnectSetting implements Setting {

    public static final String condition = String.valueOf(ONETOONE_END_SETTING);
    public static final String consoleCondition = ONETOONE_END_SETTING.symbol;

    private static volatile OneToOneDisconnectSetting instance;

    private OneToOneDisconnectSetting() {}

    public static OneToOneDisconnectSetting getInstance() {
        if (instance == null) {
            synchronized (OneToOneDisconnectSetting.class) {
                if (instance == null) {
                    instance = new OneToOneDisconnectSetting();
                }
            }
        }
        return instance;
    }

    @Override
    public void changeMySetting(String message) {
        if (UserSocket.getUser().getPartnerUsername() != null) {
//            disconnectPartner();
        }
    }

//    private void disconnectPartner() {
//        try {
//            System.out.println("귓속말 연결을 끊습니다.");
//            UserSocket.getUser().setPartnerUsername(null);
//            serverSocket.close();
//            serverSocket = null;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
