package com.alibaba.dubbo.remoting.exchange;

//响应回调
public interface ResponseCallback {

    //处理执行完成
    void done(Object response);

    //处理发生异常
    void caught(Throwable exception);

}