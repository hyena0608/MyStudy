package com.hyunseo.entity.channel;

public enum ChannelRoom {
    CHANNEL1("01", "001", "002", "003"),
    CHANNEL2("02", "001", "002", "003"),
    CHANNEL3("03", "001", "002", "003");

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

    public static String info() {

        StringBuffer sb = new StringBuffer();
        sb.append("-------------------------\n")
                .append("채널 : ")
                .append(ChannelRoom.CHANNEL1.channel)
                .append("\n")
                .append("방 제목 : ")
                .append(ChannelRoom.CHANNEL1.roomOne)
                .append(", ")
                .append(ChannelRoom.CHANNEL1.roomTwo)
                .append(", ")
                .append(ChannelRoom.CHANNEL1.roomThree)
                .append("\n");

        sb.append("-------------------------\n")
                .append("채널 : ")
                .append(ChannelRoom.CHANNEL2.channel)
                .append("\n")
                .append("방 제목 : ")
                .append(ChannelRoom.CHANNEL2.roomOne)
                .append(", ")
                .append(ChannelRoom.CHANNEL2.roomTwo)
                .append(", ")
                .append(ChannelRoom.CHANNEL2.roomThree)
                .append("\n");

        sb.append("-------------------------\n")
                .append("채널 : ")
                .append(ChannelRoom.CHANNEL3.channel)
                .append("\n")
                .append("방 제목 : ")
                .append(ChannelRoom.CHANNEL3.roomOne)
                .append(", ")
                .append(ChannelRoom.CHANNEL3.roomTwo)
                .append(", ")
                .append(ChannelRoom.CHANNEL3.roomThree)
                .append("\n");

        return sb.toString();
    }
}
