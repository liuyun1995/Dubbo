package com.alibaba.dubbo.rpc.filter.tps;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;

//TPS限制器接口
public interface TPSLimiter {

    /**
     * judge if the current invocation is allowed by TPS rule
     *
     * 根据 tps 限流规则判断是否限制此次调用.
     *
     * @param url        url
     * @param invocation invocation
     * @return true allow the current invocation, otherwise, return false
     */
    boolean isAllowable(URL url, Invocation invocation);

}
