package clientserver;

public class MessageParser {

    public static String[] messageParse(String message) {
        return message.split("\\|");
    }

}
