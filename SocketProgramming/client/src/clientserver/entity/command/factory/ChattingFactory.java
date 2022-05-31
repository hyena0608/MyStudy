package clientserver.entity.command.factory;

import clientserver.entity.command.OneToOneChatting;
import clientserver.entity.command.RoomChatting;
import clientserver.entity.command.base.Chatting;
import clientserver.entity.user.User;

public class ChattingFactory {
    private Chatting chatting = null;

    public Chatting createChatting() {

        if (User.getUserCondition().equals(RoomChatting.condition)) {
            chatting = RoomChatting.getInstance();
        } else if (User.getUserCondition().equals(OneToOneChatting.condition)) {
            chatting = OneToOneChatting.getInstance();
        }

        return chatting;
    }

}
