package chatserver.server;

import chatserver.client.ClientInfo;

public class ServerHandler implements Runnable {
    private ClientInfo clientInfo;
    private ServerSender serverSender;
    private ServerReceiver serverReceiver;

    public ServerHandler(ClientInfo clientInfo, ServerSender serverSender, ServerReceiver serverReceiver) {
        this.clientInfo = clientInfo;
        this.serverSender = serverSender;
        this.serverReceiver = serverReceiver;
    }

    @Override
    public void run() {
        while (clientInfo.getSocket().isConnected()) {
            System.out.println("실행");
            String messageFromClient = serverReceiver.getMessageFromClient();
            if (messageFromClient != null) {
                serverSender.broadcastMessage(messageFromClient);
            }
        }
    }
}
