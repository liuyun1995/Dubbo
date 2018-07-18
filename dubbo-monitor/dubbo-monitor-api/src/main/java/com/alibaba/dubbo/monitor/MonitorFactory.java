package com.alibaba.dubbo.monitor;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

//监控器工厂
@SPI("dubbo")
public interface MonitorFactory {

    //创建监控器
    @Adaptive("protocol")
    Monitor getMonitor(URL url);

}