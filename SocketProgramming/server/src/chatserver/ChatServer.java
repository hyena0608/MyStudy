package chatserver;

import chatserver.xx.Client;
import chatserver.xx.ClientRoom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

//    private ClientManager clientManager;
    private ClientRoom clientRoom;

    public void chatServerStart() {
        Socket socket;
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            settingClientRoom();
            while (true) {
                socket = serverSocket.accept();
//                setClientManagerAndStart(socket);

                settingClient(socket);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private void setClientManagerAndStart(Socket socket) {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("입장할 채널을 입력해주세요. : [ChOne | ChTwo | ChThree]");
//        String channel = sc.nextLine();
//        clientManager = new ClientManager(socket);
//        new Thread(clientManager).start();
//        System.out.println("[" + socket.getRemoteSocketAddress() + "] 에서 서버에 접근합니다.");
//    }

    private void settingClientRoom() {
        clientRoom = new ClientRoom();
        new Thread(clientRoom).start();
    }

    private void settingClient(Socket socket) {
        Client client = new Client(socket);
        new Thread(client).start();
        clientRoom.addClient(client);
        System.out.println("[" + socket.getRemoteSocketAddress() + "] 에서 서버에 접근합니다.");
    }
}
