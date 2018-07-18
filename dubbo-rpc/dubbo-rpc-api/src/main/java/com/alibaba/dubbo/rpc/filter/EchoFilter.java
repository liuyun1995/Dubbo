package com.alibaba.dubbo.rpc.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;

//回声过滤器
@Activate(group = Constants.PROVIDER, order = -110000)
public class EchoFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation inv) throws RpcException {
        // 方法名为 `$echo` ，参数只有一个
        if (inv.getMethodName().equals(Constants.$ECHO) && inv.getArguments() != null && inv.getArguments().length == 1) {
            return new RpcResult(inv.getArguments()[0]);
        }
        return invoker.invoke(inv);
    }

}