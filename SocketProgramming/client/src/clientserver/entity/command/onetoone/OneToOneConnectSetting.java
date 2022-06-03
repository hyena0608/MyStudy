package clientserver.entity.command.onetoone;

import clientserver.UserSocket;
import clientserver.entity.command.base.Setting;
import clientserver.entity.message.MessageObject;
import com.google.gson.Gson;

public class OneToOneConnectSetting implements Setting {

    public static final String condition = String.valueOf(OneToOne.ONETOTONE_CONNECT);

    private static volatile OneToOneConnectSetting instance;

    private OneToOneConnectSetting() {}

    public static OneToOneConnectSetting getInstance() {
        if (instance == null) {
            synchronized (OneToOneConnectSetting.class) {
                if (instance == null) {
                    instance = new OneToOneConnectSetting();
                }
            }
        }
        return instance;
    }

    @Override
    public void changeMySetting(String message) {
        MessageObject messageObject = new Gson().fromJson(message, MessageObject.class);
        UserSocket
                .getUser()
                .setPartnerUsername(messageObject.getUser().getUsername());
        System.out.println(UserSocket.getUser().getUsername()
                            + "의 파트너는 "
                            + UserSocket.getUser().getPartnerUsername()
                            + "이다.");
    }
}
