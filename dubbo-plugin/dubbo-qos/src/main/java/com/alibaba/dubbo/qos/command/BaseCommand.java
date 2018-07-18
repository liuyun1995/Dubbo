package com.alibaba.dubbo.qos.command;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface BaseCommand {
    String execute(CommandContext commandContext,String[] args);
}
