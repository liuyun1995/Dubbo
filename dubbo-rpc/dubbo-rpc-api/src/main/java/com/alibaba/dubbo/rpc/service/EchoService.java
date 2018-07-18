package com.alibaba.dubbo.rpc.service;

/**
 * Echo service.
 *
 * 回音服务，用于监控。
 *
 * @export
 */
public interface EchoService {

    /**
     * echo test.
     *
     * @param message message.
     * @return message.
     */
    Object $echo(Object message);

}