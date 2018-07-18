package com.alibaba.dubbo.rpc.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

//类加载器切换过滤器
@Activate(group = Constants.PROVIDER, order = -30000)
public class ClassLoaderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 获得原来的类加载器
        ClassLoader ocl = Thread.currentThread().getContextClassLoader();
        // 切换当前线程的类加载器为服务接口的类加载器
        Thread.currentThread().setContextClassLoader(invoker.getInterface().getClassLoader());
        // 服务调用
        try {
            return invoker.invoke(invocation);
        } finally {
            // 切换当前线程的类加载器为原来的类加载器
            Thread.currentThread().setContextClassLoader(ocl);
        }
    }

}