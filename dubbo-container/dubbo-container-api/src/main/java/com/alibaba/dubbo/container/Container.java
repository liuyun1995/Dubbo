package com.alibaba.dubbo.container;

import com.alibaba.dubbo.common.extension.SPI;

//容器接口
@SPI("spring")
public interface Container {

    //启动
    void start();

    //停止
    void stop();

}