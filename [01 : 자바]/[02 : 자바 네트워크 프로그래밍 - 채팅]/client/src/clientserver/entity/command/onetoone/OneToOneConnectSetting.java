package clientserver.entity.command.onetoone;

import clientserver.service.socket.parser.SocketMessageParserImpl;
import clientserver.socket.UserSocket;
import clientserver.entity.command.base.Setting;
import clientserver.entity.message.MessageObject;


import static clientserver.entity.command.onetoone.OneToOneType.ONETOONE_CHATTING;
import static clientserver.entity.command.onetoone.OneToOneType.ONETOONE_CONNECT_SETTING;

public class OneToOneConnectSetting implements Setting {

    public static final String condition = String.valueOf(ONETOONE_CONNECT_SETTING);
    private static volatile OneToOneConnectSetting instance;

    private OneToOneConnectSetting() {}

    public static OneToOneConnectSetting getInstance() {
        if (instance == null) {
            synchronized (OneToOneConnectSetting.class) {
                if (instance == null) {
                    instance = new OneToOneConnectSetting();
                }
            }
        }
        return instance;
    }

    @Override
    public void changeMySetting(String message) {
        MessageObject messageObject = new SocketMessageParserImpl().toObject(message);

        changeMyPartnerUsername(messageObject);
        changeMyUserCondition(String.valueOf(ONETOONE_CHATTING));

        System.out.println(UserSocket.getUser().getPartnerUsername()
                            + "님과의 귓속말이 시작되었습니다.");

    }

    private void changeMyPartnerUsername(MessageObject messageObject) {
        String username = messageObject
                                .getUser()
                                .getUsername();

        String partnerUsername = messageObject
                                    .getUser()
                                    .getPartnerUsername();

        if (isUserSocketRight(username)) {
            UserSocket
                    .getUser()
                    .setPartnerUsername(partnerUsername);

            UserSocket
                    .getUser()
                    .setUserCondition(String.valueOf(ONETOONE_CHATTING));
        }
    }

    private void changeMyUserCondition(String userCondition) {
        UserSocket
                .getUser()
                .setUserCondition(userCondition);
    }

    private boolean isUserSocketRight(String username) {
        return UserSocket
                    .getUser()
                    .getUsername()
                    .equals(username);
    }

}
