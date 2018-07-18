package com.alibaba.dubbo.rpc.cluster.support;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Cluster;
import com.alibaba.dubbo.rpc.cluster.Directory;

/**
 * AvailableCluster
 *
 * 失败自动切换，当出现失败，重试其它服务器。通常用于读操作，但重试会带来更长延迟。可通过 retries="2" 来设置重试次数(不含第一次)。
 */
public class AvailableCluster implements Cluster {

    public static final String NAME = "available";

    public <T> Invoker<T> join(Directory<T> directory) throws RpcException {
        return new AvailableClusterInvoker<T>(directory);
    }

}