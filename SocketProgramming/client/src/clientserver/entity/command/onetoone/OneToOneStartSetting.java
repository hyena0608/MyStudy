package clientserver.entity.command.onetoone;

import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;
import clientserver.service.socket.parser.SocketMessageParserImpl;
import clientserver.socket.SocketType;
import clientserver.socket.UserSocket;
import clientserver.entity.command.base.Setting;

import java.util.Scanner;

import static clientserver.entity.command.onetoone.OneToOneType.*;
import static clientserver.socket.SocketType.USER_SOCKET;

public class OneToOneStartSetting implements Setting {

    private static volatile OneToOneStartSetting instance;
    private SocketType socketType = USER_SOCKET;
    public static final String condition = String.valueOf(ONETOONE_START_SETTING);
    public static final String consoleCondition = ONETOONE_START_SETTING.symbol;


    private OneToOneStartSetting() {}

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
        if (UserSocket
                .getUser()
                .getPartnerUsername() == null) {
            connectPartner();
        }
    }

    private void connectPartner() {
        Scanner sc = new Scanner(System.in);

        System.out.print("상대이름을 입력하세요. : ");
        String partnerUsername = sc.nextLine();

        UserSocket
                .getUser()
                .setPartnerUsername(partnerUsername);

        MessageObject messageObject = new MessageObjectBuilder()
                .setMessageType(String.valueOf(ONETOONE_CONNECT_SETTING))
                .setUser(UserSocket.getUser())
                .setContent("")
                .build();

        new SocketMessageHandlerImpl()
                .send(
                        String.valueOf(socketType),
                        new SocketMessageParserImpl().toJson(messageObject)
                );
    }
}
