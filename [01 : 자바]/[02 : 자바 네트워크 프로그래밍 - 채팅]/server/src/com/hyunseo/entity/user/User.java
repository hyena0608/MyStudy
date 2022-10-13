package com.hyunseo.entity.user;

public class User {
    private String username;
    private String userCondition;
    private String partnerUsername;

    private String channelTitle;
    private String roomTitle;
    private int oneToOnePort;

    public User(String username, String userCondition, String partnerUsername, int oneToOnePort, String channelTitle, String roomTitle) {
        this.username = username;
        this.userCondition = userCondition;
        this.partnerUsername = partnerUsername;
        this.oneToOnePort = oneToOnePort;
        this.channelTitle = channelTitle;
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

    public int getOneToOnePort() {
        return oneToOnePort;
    }

    public void setOneToOnePort(int oneToOnePort) {
        this.oneToOnePort = oneToOnePort;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserCondition(String userCondition) {
        this.userCondition = userCondition;
    }

    public void setPartnerUsername(String partnerUsername) {
        this.partnerUsername = partnerUsername;
    }
}
