package clientserver.entity.command.onetoone;

public enum OneToOne {
    ONETOONE_START_SETTING("/귓속말"),
    ONETOONE_END_SETTING("/종료"),
    ONETOONE_CONNECT_SETTING("ONETOONE_CONNECT_SETTING"),
    ONETOONE_CHATTING("ONETOONE_CHATTING");

    public String symbol;

    OneToOne(String symbol) {
        this.symbol = symbol;
    }
}
