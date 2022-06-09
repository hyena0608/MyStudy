package com.hyunseo.entity.channel;

public enum ChannelRoom {
    CHANNEL1("01", "001", "002", "003"),
    CHANNEL2("01", "001", "002", "003"),
    CHANNEL3("01", "001", "002", "003");

    public String channel;
    public String roomOne;
    public String roomTwo;
    public String roomThree;

    ChannelRoom(String channel, String roomOne, String roomTwo, String roomThree) {
        this.channel = channel;
        this.roomOne = roomOne;
        this.roomTwo = roomTwo;
        this.roomThree = roomThree;
    }
}
