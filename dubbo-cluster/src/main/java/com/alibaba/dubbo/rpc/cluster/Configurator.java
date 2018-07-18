package com.alibaba.dubbo.rpc.cluster;

import com.alibaba.dubbo.common.URL;

/**
 * Configurator. (SPI, Prototype, ThreadSafe)
 *
 * Configurator 接口
 */
public interface Configurator extends Comparable<Configurator> {

    /**
     * get the configurator url.
     *
     * 配置规则 URL
     *
     * @return configurator url.
     */
    URL getUrl();

    /**
     * Configure the provider url.
     *
     * 配置到 URL 中
     *
     * @param url - old rovider url.
     * @return new provider url.
     */
    URL configure(URL url);

}