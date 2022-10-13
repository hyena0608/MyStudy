package clientserver.entity.command.file;

public enum FileType {
    FILE_SENDER_SETTING("/파일전송"),
    FILE_RECEIVE_CHATTING("FILE_RECEIVE_CHATTING"),
    FILE_SENDER_CHATTING("/파일전송");

    public String command;

    FileType(String command) {
        this.command = command;
    }
}
