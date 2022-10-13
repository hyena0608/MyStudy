package clientserver.entity.command.onetoone;

import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;
import clientserver.service.socket.parser.SocketMessageParserImpl;
import clientserver.socket.UserSocket;
import clientserver.entity.command.base.Setting;

import java.util.Scanner;

import static clientserver.entity.command.onetoone.OneToOneType.*;

public class  OneToOneStartSetting implements Setting {

    private static volatile OneToOneStartSetting instance;
    public static final String condition = String.valueOf(ONETOONE_START_SETTING);
    public static final String consoleCondition = ONETOONE_START_SETTING.command;


    private OneToOneStartSetting() {
    }

    public static OneToOneStartSetting getInstance() {
        if (instance == null) {
            synchronized (OneToOneStartSetting.class) {
                if (instance == null) {
                    instance = new OneToOneStartSetting();
                }
            }
        }
        return instance;
    }

    @Override
    public void changeMySetting(String message) {
        if (!isPartnerExist()) {
            connectPartner();
        }
    }

    private boolean isPartnerExist() {
        if (UserSocket.getUser().getPartnerUsername() != null) {
            return true;
        }
        return false;
    }

    private void connectPartner() {
        String partnerUsername = scanPartner();

        setMyPartnerUsername(partnerUsername);
        setMyUserCondition(OneToOneConnectSetting.condition);

        MessageObject messageObject = new MessageObjectBuilder()
                .setMessageType(OneToOneConnectSetting.condition)
                .setUser(UserSocket.getUser())
                .build();

        new SocketMessageHandlerImpl()
                .send(new SocketMessageParserImpl().toJson(messageObject));

        setMyUserCondition(String.valueOf(ONETOONE_CHATTING));

        System.out.println(partnerUsername + "님과의 귓속말이 시작되었습니다.");
    }

    private String scanPartner() {
        Scanner sc = new Scanner(System.in);

        System.out.print("상대이름을 입력하세요. : ");
        return sc.nextLine();
    }

    private void setMyPartnerUsername(String partnerUsername) {
        UserSocket
                .getUser()
                .setPartnerUsername(partnerUsername);
    }

    private void setMyUserCondition(String userCondition) {
        UserSocket
                .getUser()
                .setUserCondition(userCondition);
    }
}
