package com.alibaba.dubbo.rpc.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcInvocation;

/**
 * ConsumerContextInvokerFilter
 *
 * 服务消费者的 ContextFilter
 */
@Activate(group = Constants.CONSUMER, order = -10000)
public class ConsumerContextFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 设置 RpcContext 对象
        RpcContext.getContext()
                .setInvoker(invoker)
                .setInvocation(invocation)
                .setLocalAddress(NetUtils.getLocalHost(), 0) // 本地地址
                .setRemoteAddress(invoker.getUrl().getHost(), invoker.getUrl().getPort()); // 远程地址
        // 设置 RpcInvocation 对象的 `invoker` 属性
        if (invocation instanceof RpcInvocation) {
            ((RpcInvocation) invocation).setInvoker(invoker);
        }
        // 服务调用
        try {
            return invoker.invoke(invocation);
        } finally {
            // 清理隐式参数集合
            RpcContext.getContext().clearAttachments();
        }
    }

}