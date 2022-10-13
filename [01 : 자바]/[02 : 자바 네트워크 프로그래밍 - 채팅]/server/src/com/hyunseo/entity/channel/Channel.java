package com.hyunseo.entity.channel;

import java.util.HashMap;
import java.util.Map;

public class Channel {

    private static Map<String, Map<String, Room>> channelMap = new HashMap<>();

    public static Map<String, Map<String, Room>> getChannelMap() {
        return channelMap;
    }
}
