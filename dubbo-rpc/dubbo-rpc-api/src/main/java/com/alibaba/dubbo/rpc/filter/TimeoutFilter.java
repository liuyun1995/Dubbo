package com.alibaba.dubbo.rpc.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

import java.util.Arrays;

//超时过滤器
@Activate(group = Constants.PROVIDER)
public class TimeoutFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(TimeoutFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long start = System.currentTimeMillis();
        // 服务调用
        Result result = invoker.invoke(invocation);
        // 计算调用时长
        long elapsed = System.currentTimeMillis() - start;
        // 超过时长，打印告警日志
        if (invoker.getUrl() != null
                && elapsed > invoker.getUrl().getMethodParameter(invocation.getMethodName(), "timeout", Integer.MAX_VALUE)) {
            if (logger.isWarnEnabled()) {
                logger.warn("invoke time out. method: " + invocation.getMethodName()
                        + " arguments: " + Arrays.toString(invocation.getArguments()) + " , url is "
                        + invoker.getUrl() + ", invoke elapsed " + elapsed + " ms.");
            }
        }
        return result;
    }

}