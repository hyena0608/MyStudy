package clientserver.message.console;

public interface MessageConsole {
    StringBuffer stringBuffer = new StringBuffer();

    abstract void print(String json);
}
