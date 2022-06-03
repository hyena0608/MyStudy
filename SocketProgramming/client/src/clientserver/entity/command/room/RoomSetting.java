package clientserver.entity.command.room;

import clientserver.entity.command.base.Setting;

import static clientserver.entity.command.room.Room.ROOM_SETTING;

public class RoomSetting implements Setting {

    private static volatile RoomSetting instance;
    private static final String condition = String.valueOf(ROOM_SETTING);

    private RoomSetting() {

    }

    public static RoomSetting getInstance() {
        if (instance == null) {
            synchronized (RoomSetting.class) {
                if (instance == null) {
                    instance = new RoomSetting();
                }
            }
        }
        return instance;
    }

    @Override
    public void changeMySetting(String messageJson) {

    }

}
