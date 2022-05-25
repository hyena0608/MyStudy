package clientserver.utility;

import clientserver.message.form.MessageForm;
import com.google.gson.Gson;

public class MyJsonParser {
    private static Gson gson = new Gson();

    public static MessageForm toObject(String jsonMessage) {
        return gson.fromJson(jsonMessage, MessageForm.class);
    }

    public static String toJson(MessageForm messageForm) {
        return gson.toJson(messageForm);
    }

}
