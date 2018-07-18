package com.alibaba.dubbo.remoting.exchange;

import com.alibaba.dubbo.remoting.Channel;
import com.alibaba.dubbo.remoting.RemotingException;

//信息交换通道接口
public interface ExchangeChannel extends Channel {

    //发送请求
    ResponseFuture request(Object request) throws RemotingException;

    //发送请求
    ResponseFuture request(Object request, int timeout) throws RemotingException;

    //获得信息交换处理器
    ExchangeHandler getExchangeHandler();

    //优雅关闭
    void close(int timeout);

}