package clientserver.entity.command.file;

import clientserver.entity.command.base.Chatting;
import clientserver.entity.message.MessageObject;
import clientserver.service.file.FileReceiver;
import clientserver.socket.UserSocket;

import java.io.File;
import java.util.Scanner;

import static clientserver.entity.command.file.FileType.FILE_RECEIVE_CHATTING;
import static clientserver.entity.command.file.FileType.FILE_SENDER_CHATTING;

public class FileReceiverChatting implements Chatting {
    private static volatile FileReceiverChatting instance;
    public static final String condition = String.valueOf(FILE_RECEIVE_CHATTING);

    public static FileReceiverChatting getInstance() {
        if (instance == null) {
            synchronized (FileReceiverChatting.class) {
                if (instance == null) {
                    instance = new FileReceiverChatting();
                }
            }
        }
        return instance;
    }

    @Override
    public void sendMessage(MessageObject messageObject) {
        // TODO : 상대방에게 받았다고 메시지 전달
    }

    @Override
    public void consoleMessage(MessageObject messageObject) {
        System.out.print (UserSocket.getUser().getPartnerUsername() + "님으로부터 파일을 받으시겠습니까? [y|n]");
        String answer = new Scanner(System.in).nextLine();
        if (answer.equals("y")) {
            int port = messageObject.getUser().getOneToOnePort();

            FileReceiver fileReceiver = new FileReceiver(port);
            Thread fileReceiverThread = new Thread(fileReceiver);
            fileReceiverThread.start();
            fileReceiverThread.interrupt();
            System.out.println("파일 다운로드가 완료 되었습니다.");
        } else if (answer.equals("n")) {
            System.out.println("요청을 거부했습니다.");
        }
    }
}
