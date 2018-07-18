package com.alibaba.dubbo.remoting.exchange;

import com.alibaba.dubbo.remoting.ChannelHandler;
import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.telnet.TelnetHandler;

//信息交换处理器接口
public interface ExchangeHandler extends ChannelHandler, TelnetHandler {

    //回复请求结果
    Object reply(ExchangeChannel channel, Object request) throws RemotingException;

}