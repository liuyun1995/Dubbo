package com.alibaba.dubbo.rpc.cluster;

import com.alibaba.dubbo.common.extension.SPI;

//合并接口
@SPI
public interface Merger<T> {

    //合并方法
    T merge(T... items);

}