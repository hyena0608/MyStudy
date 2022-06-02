package clientserver.entity.command;

import clientserver.UserSocket;
import clientserver.entity.command.base.Setting;
import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.service.console.parser.ConsoleMessageParserImpl;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class OneToOneSetting implements Setting {

    private static volatile OneToOneSetting instance;
    private static ServerSocket serverSocket = null;
    private static Scanner sc = new Scanner(System.in);
    public static final String startConditionByConsole = "/귓속말";
    public static final String endConditionByConsole = "/종료";
    public static final String condition = "ONETOONESETTING";

    private OneToOneSetting() {}

    public static OneToOneSetting getInstance() {
        if (instance == null) {
            synchronized (OneToOneSetting.class) {
                if (instance == null) {
                    instance = new OneToOneSetting();
                }
            }
        }
        return instance;
    }

    @Override
    public void changeMySetting() {
        if (UserSocket.getUser().getPartnerUsername() == null) {
            connectPartner();
        } else if (UserSocket.getUser().getPartnerUsername() != null) {
            System.out.println("연결을 끊으시겠습니까? y/n");
            String answer = sc.nextLine();
            if (answer.equals("y")) {
                disconnectPartner();
            } else if (answer.equals("n")) {}
        }
    }

    private void isForConnection() {

    }

    private void isForDisConnection() {

    }

    private void connectPartner() {

        // 이름 입력
        System.out.print("상대이름을 입력하세요. : ");
        String partnerUsername = sc.nextLine();
        UserSocket.getUser().setPartnerUsername(partnerUsername);

        // 서버 소켓 오픈
        System.out.println("소켓을 엽니다.");
        try {
            serverSocket = new ServerSocket(10000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 새로 오픈한 서버 소켓 포트 번호 상대방에게 전달하기
        System.out.println("포트 번호를 상대에게 전달합니다.");
        // JSON 형태로 변경합니다.
        MessageObject messageObject = new MessageObjectBuilder()
                .setContent("10000")
                .setUser(UserSocket.getUser())
                .setMessageType(OneToOneSetting.condition)
                .build();

        new SocketMessageHandlerImpl().send(
                new ConsoleMessageParserImpl().toJson(messageObject)
        );

        System.out.println("포트 번호 : ["
                            + messageObject.getContent() + "], "
                            + messageObject.getUser()
                            + "님에게 귓속말 정보가 전달 되었습니다.");
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