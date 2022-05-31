package clientserver.entity.command.factory;

import clientserver.UserSocket;
import clientserver.entity.command.OneToOneChatting;
import clientserver.entity.command.RoomChatting;
import clientserver.entity.command.base.Chatting;

public class ChattingFactory {
    private Chatting chatting = null;

    public Chatting createChatting() {

        if (UserSocket.getUser().getUserCondition().equals(RoomChatting.condition)) {
            chatting = RoomChatting.getInstance();
        } else if (UserSocket.getUser().getUserCondition().equals(OneToOneChatting.condition)) {
            chatting = OneToOneChatting.getInstance();
        }

        return chatting;
    }

}
