package chatserver;

import chatserver.channel.ChannelHandler;
import chatserver.client.ClientInfo;
import chatserver.server.ServerHandler;
import chatserver.server.ServerReceiver;
import chatserver.server.ServerSender;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BaseChatServer {

    ServerSocket serverSocket;
    ChannelHandler channelHandler;

    public void chatServerStart() {
        try {
            serverSocket = new ServerSocket(8888);
            channelHandler = new ChannelHandler();

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                ClientInfo clientInfo = new ClientInfo(socket);
                channelHandler.addClientToRoom(clientInfo);
                ServerSender serverSender = new ServerSender(clientInfo);
                ServerReceiver serverReceiver = new ServerReceiver(clientInfo);
                ServerHandler serverHandler = new ServerHandler(clientInfo, serverSender, serverReceiver);

                new Thread(serverHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}