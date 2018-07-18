package com.alibaba.dubbo.rpc;

import com.alibaba.dubbo.common.Node;

//调用接口
public interface Invoker<T> extends Node {

    //获取服务接口
    Class<T> getInterface();

    //方法调用
    Result invoke(Invocation invocation) throws RpcException;

}