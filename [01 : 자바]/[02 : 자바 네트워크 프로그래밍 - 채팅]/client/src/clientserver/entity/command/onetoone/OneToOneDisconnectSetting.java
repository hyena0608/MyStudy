package clientserver.entity.command.onetoone;

import clientserver.entity.command.room.RoomChatting;
import clientserver.socket.UserSocket;
import clientserver.entity.command.base.Setting;

import static clientserver.entity.command.onetoone.OneToOneType.ONETOONE_DISCONNECT_SETTING;

public class OneToOneDisconnectSetting implements Setting {

    public static final String condition = String.valueOf(ONETOONE_DISCONNECT_SETTING);
    public static final String consoleCondition = ONETOONE_DISCONNECT_SETTING.command;
    private static volatile OneToOneDisconnectSetting instance;

    private OneToOneDisconnectSetting() {
    }

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
        if (isPartnerExist()) {
            sendDisconnectOneToOneToPartner();
            disconnectPartner();
        }
    }

    private boolean isPartnerExist() {
        if (UserSocket.getUser().getPartnerUsername() != null) {
            return true;
        }
        return false;
    }

    private void disconnectPartner() {
        makePartnerNull();
        changeUserCondition();
    }

    private void changeUserCondition() {
        UserSocket
                .getUser()
                .setUserCondition(RoomChatting.condition);
    }

    private void makePartnerNull() {
        System.out.println(UserSocket.getUser().getPartnerUsername()
                            + "님과의 귓속말이 종료 되었습니다.");
        UserSocket
                .getUser()
                .setPartnerUsername(null);
    }

    private void sendDisconnectOneToOneToPartner() {
    }
}
