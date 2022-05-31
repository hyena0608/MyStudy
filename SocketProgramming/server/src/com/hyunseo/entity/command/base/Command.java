package com.hyunseo.entity.command.base;


public interface Command {

    abstract void send(String messageJson);

}
