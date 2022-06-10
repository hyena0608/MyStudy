package clientserver.entity.command.file;

public enum FileType {
    FILE_SEND("/파일전송"),
    FILE_RECEIVE("파일수신");

    public String command;

    FileType(String command) {
        this.command = command;
    }
}
