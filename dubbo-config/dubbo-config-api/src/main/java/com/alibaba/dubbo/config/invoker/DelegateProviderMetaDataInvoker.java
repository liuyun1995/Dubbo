package com.alibaba.dubbo.config.invoker;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

public class DelegateProviderMetaDataInvoker<T> implements Invoker {

    /**
     * Invoker 对象
     */
    protected final Invoker<T> invoker;
    /**
     * 服务提供者配置
     */
    private ServiceConfig metadata;

    public DelegateProviderMetaDataInvoker(Invoker<T> invoker,ServiceConfig metadata) {
        this.invoker = invoker;
        this.metadata = metadata;
    }

    public Class<T> getInterface() {
        return invoker.getInterface();
    }

    public URL getUrl() {
        return invoker.getUrl();
    }

    public boolean isAvailable() {
        return invoker.isAvailable();
    }

    @Override
    public Result invoke(Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }

    public void destroy() {
        invoker.destroy();
    }

    public ServiceConfig getMetadata() {
        return metadata;
    }
}
