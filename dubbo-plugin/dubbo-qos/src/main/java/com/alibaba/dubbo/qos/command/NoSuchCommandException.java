package com.alibaba.dubbo.qos.command;

public class NoSuchCommandException extends Exception {

    public NoSuchCommandException(String msg) {
        super("NoSuchCommandException:" + msg);
    }

}
