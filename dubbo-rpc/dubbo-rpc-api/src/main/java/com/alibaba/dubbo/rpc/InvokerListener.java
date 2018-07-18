package com.alibaba.dubbo.rpc;

import com.alibaba.dubbo.common.extension.SPI;

//调用者监听器
@SPI
public interface InvokerListener {

    //当服务引用完成
    void referred(Invoker<?> invoker) throws RpcException;

    //当服务销毁引用完成
    void destroyed(Invoker<?> invoker);

}