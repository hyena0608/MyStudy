package clientserver.entity.command.room;

import clientserver.entity.command.base.Setting;

public class RoomSetting implements Setting {

    private static volatile RoomSetting instance;
    private static final String condition = "ROOMSETTING";

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
