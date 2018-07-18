package com.alibaba.dubbo.qos.command;

public class CommandContextFactory {

    public static CommandContext newInstance(String commandName){
        return new CommandContext(commandName);
    }

    public static CommandContext newInstance(String commandName, String[] args,boolean isHttp){
        return new CommandContext(commandName,args,isHttp);
    }

}
