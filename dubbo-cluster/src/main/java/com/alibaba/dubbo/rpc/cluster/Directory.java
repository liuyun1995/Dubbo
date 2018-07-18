package com.alibaba.dubbo.rpc.cluster;

import com.alibaba.dubbo.common.Node;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;

import java.util.List;

//目录接口
public interface Directory<T> extends Node {

    //获得服务类型
    Class<T> getInterface();

    //获得所有服务Invoker集合
    List<Invoker<T>> list(Invocation invocation) throws RpcException;

}