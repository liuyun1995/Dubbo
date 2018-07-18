package com.alibaba.dubbo.qos.command;

public interface CommandExecutor {

    String execute(CommandContext commandContext) throws NoSuchCommandException;

}
