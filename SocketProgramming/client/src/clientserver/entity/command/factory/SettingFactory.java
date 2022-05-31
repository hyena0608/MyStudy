package clientserver.entity.command.factory;

import clientserver.entity.command.OneToOneSetting;
import clientserver.entity.command.base.Setting;

public class SettingFactory {
    private Setting setting = null;

    public Setting createSetting(String type) {

        if (type.equals("/귓속말")) {
            setting = OneToOneSetting.getInstance();
        }

        return setting;
    }
}
