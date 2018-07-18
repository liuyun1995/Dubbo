package com.alibaba.dubbo.remoting.telnet;

import com.alibaba.dubbo.common.extension.SPI;
import com.alibaba.dubbo.remoting.Channel;
import com.alibaba.dubbo.remoting.RemotingException;

//远程命令处理器
@SPI
public interface TelnetHandler {

    /**
     * telnet.
     * 处理 telnet 命令
     *
     * @param channel 通道
     * @param message telnet 命令
     */
    String telnet(Channel channel, String message) throws RemotingException;

}