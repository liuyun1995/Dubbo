package com.alibaba.dubbo.remoting.transport.dispatcher.message;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.ChannelHandler;
import com.alibaba.dubbo.remoting.Dispatcher;

/**
 * Only message receive uses the thread pool.
 *
 */
public class MessageOnlyDispatcher implements Dispatcher {

    public static final String NAME = "message";

    public ChannelHandler dispatch(ChannelHandler handler, URL url) {
        return new MessageOnlyChannelHandler(handler, url);
    }

}