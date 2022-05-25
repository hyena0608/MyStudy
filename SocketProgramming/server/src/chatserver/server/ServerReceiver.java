package chatserver.server;

import chatserver.client.ClientInfo;

import java.io.IOException;

public class ServerReceiver {

    private final ClientInfo clientInfo;

    public ServerReceiver(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getMessageFromClient() {
        String messageFromClient = null;
        try {
            messageFromClient = clientInfo.getIn().readUTF();
            System.out.println("[서버 수신] : '" + messageFromClient + "' 라는 메시지를 받았습니다.");
        } catch (IOException e) {

        }
        return messageFromClient;
    }

}
