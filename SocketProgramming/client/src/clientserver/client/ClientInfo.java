package clientserver.client;

import clientserver.message.form.MessageForm;
import clientserver.message.form.MessageFormBuilder;
import clientserver.utility.MyJsonParser;
import clientserver.message.console.MessageParser;

import java.io.IOException;
import java.util.Scanner;

public class ClientInfo {

    private final String username;
    private final String channel;
    private final String room;
    private String userChatCondition;

    public ClientInfo() throws IOException {
        this.username = setClientUsername();
        this.channel = setClientChannel();
        this.room = setClientRoom();
        this.userChatCondition = String.valueOf(MessageParser.ROOM);
    }

    public String clientInfoToJson() {
        MessageForm messageForm = new MessageFormBuilder()
                .setSender(this.username)
                .setReceiver(null)
                .setContent(null)
                .setMessageType(String.valueOf(MessageParser.CLIENTINFO))
                .setChannelNumber(this.channel)
                .setRoomNumber(this.room)
                .build();

        return MyJsonParser.toJson(messageForm);
    }

    Scanner sc = new Scanner(System.in);
    private String setClientUsername() {
        System.out.println("[서버] : 사용할 닉네임을 입력해주세요");
        return sc.nextLine();
    }

    private String setClientChannel() {
        System.out.println("[서버] : 입장할 채널을 입력해주세요");
        return sc.nextLine();
    }

    private String setClientRoom() {
        System.out.println("[서버] : 입장할 방을 입력해주세요");
        return sc.nextLine();
    }

    public String getUsername() {
        return username;
    }

    public String getChannel() {
        return channel;
    }

    public String getRoom() {
        return room;
    }

    public String getUserChatCondition() {
        return userChatCondition;
    }

    public void setUserChatCondition(String userChatCondition) {
        this.userChatCondition = userChatCondition;
    }
}
