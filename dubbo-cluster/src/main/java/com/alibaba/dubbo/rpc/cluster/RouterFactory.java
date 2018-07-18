package com.alibaba.dubbo.rpc.cluster;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

//路由器工厂接口
@SPI
public interface RouterFactory {

    //创建路由器对象
    @Adaptive("protocol")
    Router getRouter(URL url);

}