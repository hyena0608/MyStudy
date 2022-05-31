package clientserver.entity.user;

import clientserver.entity.command.RoomChatting;

import java.util.Scanner;

public class User {

    private String username;
    private String userCondition;
    private String partnerUsername;

    private String channelTitle;
    private String roomTitle;

    public User() {
        init();
    }

    void init() {
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
