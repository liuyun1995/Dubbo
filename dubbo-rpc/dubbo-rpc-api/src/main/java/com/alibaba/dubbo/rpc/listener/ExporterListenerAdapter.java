package com.alibaba.dubbo.rpc.listener;

import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.ExporterListener;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * ExporterListenerAdapter
 *
 * ExporterListener 适配器抽象类
 */
public abstract class ExporterListenerAdapter implements ExporterListener {

    public void exported(Exporter<?> exporter) throws RpcException { }

    public void unexported(Exporter<?> exporter) throws RpcException { }

}