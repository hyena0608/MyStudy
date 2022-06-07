package clientserver.entity.user;

import clientserver.entity.command.room.RoomChatting;

import java.util.Scanner;

public class User {

    private String username;
    private String userCondition;
    private String partnerUsername;
    private int port;

    private String channelTitle;
    private String roomTitle;

    public void init() {
        Scanner sc = new Scanner(System.in);

        System.out.print("이름을 입력하세요 : ");
        this.username = sc.nextLine();
        System.out.print("\n들어갈 채널을 입력하세요 : ");
        this.channelTitle = sc.nextLine();
        System.out.print("\n들어갈 방을 입력하세요 : ");
        this.roomTitle = sc.nextLine();
        System.out.println();
        this.userCondition = RoomChatting.condition;
    }

    public User() {
    }

    public User(String username, String userCondition, String partnerUsername, int port, String channelTitle, String roomTitle) {
        this.username = username;
        this.userCondition = userCondition;
        this.partnerUsername = partnerUsername;
        this.port = port;
        this.channelTitle = channelTitle;
        this.roomTitle = roomTitle;
    }

    public void setUserCondition(String userCondition) {
        this.userCondition = userCondition;
    }

    public void setPartnerUsername(String partnerUsername) {
        this.partnerUsername = partnerUsername;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getUserCondition() {
        return userCondition;
    }

    public String getPartnerUsername() {
        return partnerUsername;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getRoomTitle() {
        return roomTitle;
    }
}
