package clientserver.entity.command.onetoone;

public enum OneToOneType {
    ONETOONE_START_SETTING("/귓속말"),
    ONETOONE_DISCONNECT_SETTING("/종료"),
    ONETOONE_CONNECT_SETTING("ONETOONE_CONNECT_SETTING"),
    ONETOONE_CHATTING("ONETOONE_CHATTING");

    public String command;

    OneToOneType(String command) {
        this.command = command;
    }
}
