package clientserver.entity.command.factory;

import clientserver.entity.command.file.FileReceiverChatting;
import clientserver.entity.command.file.FileSenderChatting;
import clientserver.entity.command.onetoone.OneToOneChatting;
import clientserver.entity.command.room.RoomChatting;
import clientserver.entity.command.base.Chatting;


public class ChattingFactory {
    private Chatting chatting = null;

    public Chatting createChatting(String type) {

        if (type.equals(RoomChatting.condition)) {
            chatting = RoomChatting.getInstance();
        } else if (type.equals(OneToOneChatting.condition)) {
            chatting = OneToOneChatting.getInstance();
        } else if (type.equals(FileSenderChatting.consoleCondition)) {
            chatting = FileSenderChatting.getInstance();
        } else if (type.equals(FileReceiverChatting.condition)) {
            chatting = FileReceiverChatting.getInstance();
        }

        return chatting;
    }

}
