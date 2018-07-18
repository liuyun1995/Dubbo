package com.alibaba.dubbo.remoting.transport.dispatcher.direct;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.ChannelHandler;
import com.alibaba.dubbo.remoting.Dispatcher;

//重定向分派器
public class DirectDispatcher implements Dispatcher {

    public static final String NAME = "direct";

    public ChannelHandler dispatch(ChannelHandler handler, URL url) {
        return handler;
    }

}