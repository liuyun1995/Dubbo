package com.alibaba.dubbo.remoting.exchange;

import com.alibaba.dubbo.remoting.Server;

import java.net.InetSocketAddress;
import java.util.Collection;

//信息交换服务器接口
public interface ExchangeServer extends Server {

    //获得通道数组
    Collection<ExchangeChannel> getExchangeChannels();

    //获得指定通道
    ExchangeChannel getExchangeChannel(InetSocketAddress remoteAddress);

}