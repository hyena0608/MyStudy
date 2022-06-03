package clientserver.entity.command.onetoone;

import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;
import clientserver.service.socket.parser.SocketMessageParserImpl;
import clientserver.socket.UserSocket;
import clientserver.entity.command.base.Setting;

import java.util.Scanner;

import static clientserver.entity.command.onetoone.OneToOne.ONETOONE_START;
import static clientserver.entity.command.onetoone.OneToOne.ONETOONE_CONNECT;

public class OneToOneStartSetting implements Setting {

    public static final String condition = String.valueOf(ONETOONE_START);
    public static final String consoleCondition = ONETOONE_START.symbol;

    private static volatile OneToOneStartSetting instance;

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
                .setMessageType(String.valueOf(ONETOONE_CONNECT))
                .setUser(UserSocket.getUser())
                .setContent("")
                .build();

        new SocketMessageHandlerImpl()
                .send(
                        new SocketMessageParserImpl().toJson(messageObject)
                );
    }
}
