package clientserver.entity.command.onetoone;

public enum OneToOne {
    ONETOONE_START("/귓속말"),
    ONETOONE_END("/종료"),
    ONETOTONE_CONNECT("ONETOONE_CONNECT"),
    ONETOONE_CHATTING("ONETOTNE_CHATTING");

    public String symbol;

    OneToOne(String symbol) {
        this.symbol = symbol;
    }
}
 