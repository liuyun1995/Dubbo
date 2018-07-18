package com.alibaba.dubbo.rpc.support;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.AbstractProtocol;

/**
 * MockProtocol is used for generating a mock invoker by URL and type on consumer side
 *
 * MockProtocol 用于在 consumer side 通过 url 及类型生成一个 MockInvoker
 */
public final class MockProtocol extends AbstractProtocol {

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return new MockInvoker<T>(url);
    }

    @Override
    public int getDefaultPort() {
        return 0;
    }

}