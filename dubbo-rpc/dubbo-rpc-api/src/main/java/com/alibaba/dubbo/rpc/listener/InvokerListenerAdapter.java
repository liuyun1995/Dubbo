package com.alibaba.dubbo.rpc.listener;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.InvokerListener;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * InvokerListenerAdapter
 *
 * InvokerListener 适配器抽象类
 */
public abstract class InvokerListenerAdapter implements InvokerListener {

    public void referred(Invoker<?> invoker) throws RpcException { }

    public void destroyed(Invoker<?> invoker) { }

}