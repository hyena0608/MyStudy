package clientserver.entity.command.onetoone;

import clientserver.service.socket.parser.SocketMessageParserImpl;
import clientserver.socket.UserSocket;
import clientserver.entity.command.base.Setting;
import clientserver.entity.message.MessageObject;


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
        setMyOneToOnePort(messageObject.getUser().getPort());

        System.out.println(UserSocket.getUser().getUsername()
                            + "의 파트너는 "
                            + UserSocket.getUser().getPartnerUsername()
                            + "이다.");

        System.out.println("UserSocket.getUser().getPort() = " + UserSocket.getUser().getPort());
    }

    private void changeMyPartnerUsername(MessageObject messageObject) {
        String username = messageObject.getUser().getUsername();
        String partnerUsername = messageObject.getUser().getPartnerUsername();

        if (UserSocket.getUser().getUsername().equals(username)) {
            UserSocket
                    .getUser()
                    .setPartnerUsername(partnerUsername);
        }
    }

    private void setMyOneToOnePort(int port) {
        UserSocket.getUser().setPort(port);
    }

    private void startOneToOneSocket(int port) {

    }
}
