package clientserver.entity.command.onetoone;

import clientserver.UserSocket;
import clientserver.entity.command.base.Setting;
import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.service.console.parser.ConsoleMessageParserImpl;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class OneToOneStartSetting implements Setting {

    public static final String condition = String.valueOf(OneToOne.ONETOONE_START);
    public static final String consoleCondition = OneToOne.ONETOONE_START.symbol;
    public static ServerSocket serverSocket;

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

        System.out.println("소켓을 엽니다.");
        try {
            serverSocket = new ServerSocket(10000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("포트 번호를 상대에게 전달합니다.");
        MessageObject messageObject = new MessageObjectBuilder()
                .setContent("10000")
                .setUser(UserSocket.getUser())
                .setMessageType(OneToOneConnectSetting.condition)
                .build();

        new SocketMessageHandlerImpl().send(
                new ConsoleMessageParserImpl().toJson(messageObject)
        );
    }
}
