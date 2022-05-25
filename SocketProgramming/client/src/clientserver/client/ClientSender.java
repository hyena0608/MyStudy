package clientserver.client;

import clientserver.message.form.MessageForm;
import clientserver.message.form.MessageFormBuilder;
import clientserver.utility.MyJsonParser;
import clientserver.message.console.MessageParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientSender implements Runnable {

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public void sendMessageToRoom() {
        while (Client.getOut() != null) {
            try {
                MessageForm messageObject = new MessageFormBuilder()
                        .setChannelNumber(Client.getClientInfo().getChannel())
                        .setRoomNumber(Client.getClientInfo().getRoom())
                        .setSender(Client.getClientInfo().getUsername())
                        .setMessageType(String.valueOf(MessageParser.ROOM))
                        .setContent(bufferedReader.readLine())
                        .build();

                sendMessageToServer(messageObject);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessageToServer(MessageForm messageObject) {
        try {
            Client.getOut().writeUTF(MyJsonParser.toJson(messageObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    public void myMessageReader() {
//        try {
//            String myMessage = bufferedReader.readLine();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void myCommandReader(String myMessage) {
//
//    }

    @Override
    public void run() {
        System.out.println("[클라 서버] : ClientSender 실행");
        sendMessageToRoom();
    }
}
