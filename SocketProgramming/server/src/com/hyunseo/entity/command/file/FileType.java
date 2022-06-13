package com.hyunseo.entity.command.file;

public enum FileType {
    FILE_RECEIVE_CHATTING("FILE_RECEIVE_CHATTING"),
    FILE_SENDER_CHATTING("FILE_SENDER_CHATTING");

    public String command;

    FileType(String command) {
        this.command = command;
    }
}
