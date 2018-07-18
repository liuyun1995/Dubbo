package com.alibaba.dubbo.remoting.transport;

import com.alibaba.dubbo.remoting.ChannelHandler;

//通道处理器装饰者
public interface ChannelHandlerDelegate extends ChannelHandler {

    //获取通道处理器
    ChannelHandler getHandler();

}