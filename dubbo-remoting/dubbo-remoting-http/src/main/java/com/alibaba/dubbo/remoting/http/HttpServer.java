package com.alibaba.dubbo.remoting.http;

import com.alibaba.dubbo.common.Resetable;
import com.alibaba.dubbo.common.URL;

import java.net.InetSocketAddress;

/**
 * HTTP 服务器接口
 */
public interface HttpServer extends Resetable {

    /**
     * get http handler.
     *
     * @return http handler.
     */
    HttpHandler getHttpHandler();

    /**
     * get url.
     *
     * @return url
     */
    URL getUrl();

    /**
     * get local address.
     *
     * @return local address.
     */
    InetSocketAddress getLocalAddress();

    /**
     * close the channel.
     */
    void close();

    /**
     * Graceful close the channel.
     */
    void close(int timeout);

    /**
     * is bound.
     *
     * @return bound
     */
    boolean isBound();

    /**
     * is closed.
     *
     * @return closed
     */
    boolean isClosed();

}