package com.alibaba.dubbo.remoting;

import com.alibaba.dubbo.common.Resetable;

import java.net.InetSocketAddress;
import java.util.Collection;

//服务端接口
public interface Server extends Endpoint, Resetable {

    //是否绑定本地端口
    boolean isBound();

    //获取连接通道
    Collection<Channel> getChannels();

    //根据地址获取通道
    Channel getChannel(InetSocketAddress remoteAddress);

    //重置
    @Deprecated
    void reset(com.alibaba.dubbo.common.Parameters parameters);

}