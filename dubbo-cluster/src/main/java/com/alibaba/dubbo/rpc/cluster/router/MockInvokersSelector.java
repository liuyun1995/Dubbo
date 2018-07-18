package com.alibaba.dubbo.rpc.cluster.router;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Router;

import java.util.ArrayList;
import java.util.List;

public class MockInvokersSelector implements Router {

    @Override
    public <T> List<Invoker<T>> route(final List<Invoker<T>> invokers, URL url, final Invocation invocation) throws RpcException {
        // 获得普通 Invoker 集合
        if (invocation.getAttachments() == null) {
            return getNormalInvokers(invokers);
        } else {
            // 获得 "invocation.need.mock" 配置项
            String value = invocation.getAttachments().get(Constants.INVOCATION_NEED_MOCK);
            // 获得普通 Invoker 集合
            if (value == null) {
                return getNormalInvokers(invokers);
            // 获得 MockInvoker 集合
            } else if (Boolean.TRUE.toString().equalsIgnoreCase(value)) {
                return getMockedInvokers(invokers);
            }
        }
        // 其它，不匹配，直接返回 `invokers` 集合
        return invokers;
    }

    private <T> List<Invoker<T>> getMockedInvokers(final List<Invoker<T>> invokers) {
        // 不包含 MockInvoker 的情况下，直接返回 null
        if (!hasMockProviders(invokers)) {
            return null;
        }
        // 过滤掉普通 kInvoker ，创建 MockInvoker 集合
        List<Invoker<T>> sInvokers = new ArrayList<Invoker<T>>(1); // 一般情况就一个，所以设置了默认数组大小为 1 。
        for (Invoker<T> invoker : invokers) {
            if (invoker.getUrl().getProtocol().equals(Constants.MOCK_PROTOCOL)) {
                sInvokers.add(invoker);
            }
        }
        return sInvokers;
    }

    private <T> List<Invoker<T>> getNormalInvokers(final List<Invoker<T>> invokers) {
        // 不包含 MockInvoker 的情况下，直接返回 `invokers` 集合
        if (!hasMockProviders(invokers)) {
            return invokers;
        } else {
            // 若包含 MockInvoker 的情况下，过滤掉 MockInvoker ，创建普通 Invoker 集合
            List<Invoker<T>> sInvokers = new ArrayList<Invoker<T>>(invokers.size());
            for (Invoker<T> invoker : invokers) {
                if (!invoker.getUrl().getProtocol().equals(Constants.MOCK_PROTOCOL)) {
                    sInvokers.add(invoker);
                }
            }
            return sInvokers;
        }
    }

    private <T> boolean hasMockProviders(final List<Invoker<T>> invokers) {
        boolean hasMockProvider = false;
        for (Invoker<T> invoker : invokers) {
            if (invoker.getUrl().getProtocol().equals(Constants.MOCK_PROTOCOL)) { // 协议为 "mock"
                hasMockProvider = true;
                break;
            }
        }
        return hasMockProvider;
    }

    @Override
    public URL getUrl() {
        return null;
    }

    @Override
    public int compareTo(Router o) {
        return 1;
    }

}
