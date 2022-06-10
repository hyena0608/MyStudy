package clientserver.entity.command.file;

import clientserver.entity.command.base.Chatting;
import clientserver.entity.message.MessageObject;

public class FileChatting implements Chatting {

    private static volatile FileChatting instance;
    public static final String condition = String.valueOf(FileType.FILE_CHATTING.command);

    public static FileChatting getInstance() {
        if (instance == null) {
            synchronized (FileChatting.class) {
                if (instance == null) {
                    instance = new FileChatting();
                }
            }
        }
        return instance;
    }


    @Override
    public void sendMessage(MessageObject messageObject) {

    }

    @Override
    public void consoleMessage(MessageObject messageObject) {

    }
}
