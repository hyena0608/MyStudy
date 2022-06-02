package clientserver.entity.command.onetoone;

import clientserver.UserSocket;
import clientserver.entity.command.base.Setting;

import java.io.IOException;

import static clientserver.entity.command.onetoone.OneToOneStartSetting.serverSocket;

public class OneToOneEndSetting implements Setting {

    public static final String endConditionByConsole = String.valueOf(OneToOne.ONETOONE_END);
    public static final String consoleCondition = OneToOne.ONETOONE_END.symbol;

    private static volatile OneToOneEndSetting instance;

    private OneToOneEndSetting() {}

    public static OneToOneEndSetting getInstance() {
        if (instance == null) {
            synchronized (OneToOneEndSetting.class) {
                if (instance == null) {
                    instance = new OneToOneEndSetting();
                }
            }
        }
        return instance;
    }

    @Override
    public void changeMySetting(String message) {
        if (UserSocket.getUser().getPartnerUsername() != null) {
            disconnectPartner();
        }
    }

    private void disconnectPartner() {
        try {
            System.out.println("귓속말 연결을 끊습니다.");
            UserSocket.getUser().setPartnerUsername(null);
            serverSocket.close();
            serverSocket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
