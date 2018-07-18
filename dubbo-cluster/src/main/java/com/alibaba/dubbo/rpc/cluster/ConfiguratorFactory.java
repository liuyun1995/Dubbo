package com.alibaba.dubbo.rpc.cluster;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

//配置工厂
@SPI
public interface ConfiguratorFactory {

    //获取配置器
    @Adaptive("protocol")
    Configurator getConfigurator(URL url);

}