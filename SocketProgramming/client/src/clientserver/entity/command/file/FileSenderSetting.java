package clientserver.entity.command.file;

import clientserver.entity.command.base.Setting;
import clientserver.entity.message.MessageObject;
import clientserver.entity.message.MessageObjectBuilder;
import clientserver.service.socket.handler.SocketMessageHandlerImpl;
import clientserver.service.socket.parser.SocketMessageParserImpl;
import clientserver.socket.UserSocket;

import java.io.IOException;

import static clientserver.entity.command.file.FileType.FILE_SENDER_SETTING;

public class FileSenderSetting implements Setting {

    private static volatile FileSenderSetting instance;
    public static final String condition = String.valueOf(FILE_SENDER_SETTING);
    public static final String consoleCondition = FILE_SENDER_SETTING.command;

    public static FileSenderSetting getInstance() {
        if (instance == null) {
            synchronized (FileSenderSetting.class) {
                if (instance == null) {
                    instance = new FileSenderSetting();
                }
            }
        }
        return instance;
    }

    @Override
    public void changeMySetting(String message) {
        if (UserSocket.getUser().getPartnerUsername() != null) {
            System.out.println("파일 전송 준비");

            MessageObject messageObjectForPartner = new MessageObjectBuilder()
                    .setUser(UserSocket.getUser())
                    .setMessageType(String.valueOf(FileType.FILE_RECEIVE_CHATTING))
                    .build();

            String messageJson = new SocketMessageParserImpl().toJson(messageObjectForPartner);

            System.out.println(messageJson);
            FileSenderChatting.getInstance().sendMessage(messageObjectForPartner);
            new SocketMessageHandlerImpl().send(messageJson);
//            try {
//                UserSocket.getOut().writeUTF(messageJson);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        } else if (UserSocket.getUser().getPartnerUsername() == null) {
            System.out.println("귓속말 상태가 아닙니다.");
            System.out.println("파일 전송 모드가 불가능 합니다.");
        }
    }
}
