package clientserver.message.console;

import clientserver.utility.MyJsonParser;
import clientserver.message.form.MessageForm;

public class Room {
    StringBuffer stringBuffer = new StringBuffer();


    public void print(String jsonMessage) {
        MessageForm messageForm = MyJsonParser.toObject(jsonMessage);

        stringBuffer
                .append("[")
                .append(messageForm.getSender())
                .append("]")
                .append(" : ")
                .append(messageForm.getContent());

        System.out.println(stringBuffer);
    }
}
