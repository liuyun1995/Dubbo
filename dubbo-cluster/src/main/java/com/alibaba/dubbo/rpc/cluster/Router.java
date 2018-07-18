package com.alibaba.dubbo.rpc.cluster;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;

import java.util.List;

//路由规则接口
public interface Router extends Comparable<Router> {

    //获取URL
    URL getUrl();

    //路由方法
    <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException;

}