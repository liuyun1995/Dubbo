package com.alibaba.dubbo.remoting.transport;

import com.alibaba.dubbo.remoting.Channel;
import com.alibaba.dubbo.remoting.ChannelHandler;
import com.alibaba.dubbo.remoting.RemotingException;

//通道处理器适配器
public class ChannelHandlerAdapter implements ChannelHandler {

    @Override
    public void connected(Channel channel) throws RemotingException { }

    @Override
    public void disconnected(Channel channel) throws RemotingException { }

    @Override
    public void sent(Channel channel, Object message) { }

    @Override
    public void received(Channel channel, Object message) throws RemotingException { }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException { }

}