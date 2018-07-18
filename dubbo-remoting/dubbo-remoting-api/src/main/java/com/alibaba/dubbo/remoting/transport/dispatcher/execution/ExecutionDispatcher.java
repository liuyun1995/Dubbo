package com.alibaba.dubbo.remoting.transport.dispatcher.execution;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.ChannelHandler;
import com.alibaba.dubbo.remoting.Dispatcher;

/**
 * In addition to sending all the use thread pool processing
 *
 */
public class ExecutionDispatcher implements Dispatcher {

    public static final String NAME = "execution";

    public ChannelHandler dispatch(ChannelHandler handler, URL url) {
        return new ExecutionChannelHandler(handler, url);
    }

}