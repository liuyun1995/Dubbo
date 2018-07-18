package com.alibaba.dubbo.remoting;

//可解码的接口
public interface Decodeable {

    //解码
    void decode() throws Exception;

}
