package chatserver.server;

import chatserver.channel.ChannelHandler;
import chatserver.client.ClientInfo;

import java.io.IOException;

public class ServerSender {

    private final ClientInfo clientInfo;

    public ServerSender(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public void broadcastMessage(String messageFromClient) {
        for (ClientInfo clientInfoInRoom : ChannelHandler.getChannelMap()
                .get(clientInfo.getChannel())
                .get(clientInfo.getRoom())) {
            try {
                if (!clientInfoInRoom.equals(this.clientInfo)) {
                    System.out.println("[서버 송신] : [" + clientInfoInRoom.getChannel() + "] 채널에 "
                            + "[" + clientInfoInRoom.getRoom() + "] 방에 '"
                            + messageFromClient + "'라는 메시지를 전달했습니다.");
                    clientInfoInRoom.getOut().writeUTF(messageFromClient);
                }
            } catch (IOException e) {
            }
        }
    }

}
