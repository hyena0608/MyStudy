package clientserver.entity.command.factory;

import clientserver.entity.command.OneToOneSetting;
import clientserver.entity.command.UserSetting;
import clientserver.entity.command.base.Setting;

public class SettingFactory {
    private Setting setting = null;

    public Setting createSetting(String type) {

        if (type.equals(OneToOneSetting.startConditionByConsole)) {
            setting = OneToOneSetting.getInstance();
        } else if (type.equals(OneToOneSetting.endConditionByConsole)) {
            setting = OneToOneSetting.getInstance();
        } else if (type.equals(OneToOneSetting.condition)) {
            setting = OneToOneSetting.getInstance();
        } else if (type.equals(UserSetting.condition)) {
            setting = UserSetting.getInstance();
        }
        return setting;

    }
}
