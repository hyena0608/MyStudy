package com.hyunseo.entity.channel;

import java.util.HashMap;
import java.util.Map;

public class Channel {

    private Map<String, Map<String, Room>> channelMap = new HashMap<>();

    public Map<String, Map<String, Room>> getChannelMap() {
        return channelMap;
    }
}
