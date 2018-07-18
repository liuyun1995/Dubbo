package com.alibaba.dubbo.rpc.listener;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * DeprecatedProtocolFilter
 *
 * 引用废弃的服务时，打印错误日志提醒
 */
@Activate(Constants.DEPRECATED_KEY)
public class DeprecatedInvokerListener extends InvokerListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeprecatedInvokerListener.class);

    public void referred(Invoker<?> invoker) throws RpcException {
        if (invoker.getUrl().getParameter(Constants.DEPRECATED_KEY, false)) {
            LOGGER.error("The service " + invoker.getInterface().getName() + " is DEPRECATED! Declare from " + invoker.getUrl());
        }
    }

}