package com.alibaba.dubbo.remoting;

import com.alibaba.dubbo.common.URL;

import java.net.InetSocketAddress;

//终端接口
public interface Endpoint {

    //获取URL
    URL getUrl();

    //获得通道处理器
    ChannelHandler getChannelHandler();

    //获取本地地址
    InetSocketAddress getLocalAddress();

    //发送消息
    void send(Object message) throws RemotingException;

    //发送消息
    void send(Object message, boolean sent) throws RemotingException;

    //关闭通道
    void close();

    //关闭通道
    void close(int timeout);

    //开始关闭
    void startClose();

    //是否已关闭
    boolean isClosed();

}