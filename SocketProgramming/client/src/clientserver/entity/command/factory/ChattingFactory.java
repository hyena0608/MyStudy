package clientserver.entity.command.factory;

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
        }

        return chatting;
    }

}
