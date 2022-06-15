package clientserver.entity.command.file;

import clientserver.entity.command.base.Chatting;
import clientserver.entity.message.MessageObject;
import clientserver.service.file.FileSender;

import static clientserver.entity.command.file.FileType.FILE_SENDER_CHATTING;

public class FileSenderChatting implements Chatting {

    private static volatile FileSenderChatting instance;
    public static final String condition = String.valueOf(FILE_SENDER_CHATTING);
    public static final String consoleCondition = FILE_SENDER_CHATTING.command;

    public static FileSenderChatting getInstance() {
        if (instance == null) {
            synchronized (FileSenderChatting.class) {
                if (instance == null) {
                    instance = new FileSenderChatting();
                }
            }
        }
        return instance;
    }

    @Override
    public void sendMessage(MessageObject messageObject) {
        int port = messageObject.getUser().getOneToOnePort();
        FileSender fileSender = new FileSender(port);
        Thread fileSenderThread = new Thread(fileSender);
        fileSenderThread.start();
    }

    @Override
    public void consoleMessage(MessageObject messageObject) {
    }
}
