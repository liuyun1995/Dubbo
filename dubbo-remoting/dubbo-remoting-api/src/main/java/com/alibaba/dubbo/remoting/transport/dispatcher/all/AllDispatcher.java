package com.alibaba.dubbo.remoting.transport.dispatcher.all;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.ChannelHandler;
import com.alibaba.dubbo.remoting.Dispatcher;

/**
 * default thread pool configure
 *
 */
public class AllDispatcher implements Dispatcher {

    public static final String NAME = "all";

    public ChannelHandler dispatch(ChannelHandler handler, URL url) {
        return new AllChannelHandler(handler, url);
    }

}