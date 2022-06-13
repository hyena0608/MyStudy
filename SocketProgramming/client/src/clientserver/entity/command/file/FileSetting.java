package clientserver.entity.command.file;

import clientserver.entity.command.base.Chatting;
import clientserver.entity.message.MessageObject;
import clientserver.service.file.FileTransferSender;

import static clientserver.entity.command.file.FileType.FILE_CHATTING;

public class FileChatting implements Chatting {

    private static volatile FileChatting instance;
    public static final String condition = String.valueOf(FILE_CHATTING.command);

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
        // TODO : '/파일전송'을 받았을 떄 FileSender에서 처리해서 write한다. ServerSocket이 열렸음에 주의해서 구현해야 한다.
        FileTransferSender fileTransferSender = new FileTransferSender();
        Thread fileSenderThread = new Thread(fileTransferSender);
        try {
            fileSenderThread.start();
            fileSenderThread.wait();
            fileSenderThread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void consoleMessage(MessageObject messageObject) {

    }
}
