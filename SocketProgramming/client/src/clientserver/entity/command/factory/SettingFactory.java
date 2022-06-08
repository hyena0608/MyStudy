package clientserver.entity.command.factory;

import clientserver.entity.command.onetoone.OneToOneConnectSetting;
import clientserver.entity.command.onetoone.OneToOneDisconnectSetting;
import clientserver.entity.command.onetoone.OneToOneStartSetting;
import clientserver.entity.command.user.UserSetting;
import clientserver.entity.command.base.Setting;

public class SettingFactory {
    private Setting setting = null;

    public Setting createSetting(String type) {

        if (type.equals(OneToOneStartSetting.consoleCondition)) {
            setting = OneToOneStartSetting.getInstance();
        } else if (type.equals(OneToOneDisconnectSetting.consoleCondition)) {
            setting = OneToOneDisconnectSetting.getInstance();
        } else if (type.equals(OneToOneConnectSetting.condition)) {
            setting = OneToOneConnectSetting.getInstance();
        }

        if (type.equals(UserSetting.condition)) {
            setting = UserSetting.getInstance();
        }

        return setting;
    }
}
