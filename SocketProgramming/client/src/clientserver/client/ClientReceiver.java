package clientserver.client;

import clientserver.message.console.MessageParser;

import java.io.IOException;

public class ClientReceiver implements Runnable {

    public void receiveMessage() {
        try {
            while(Client.getIn() != null) {
                String jsonMessage = Client.getIn().readUTF();

                MessageParser.findMessageType(jsonMessage)
                                .printMessage(jsonMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        receiveMessage();
    }

}
