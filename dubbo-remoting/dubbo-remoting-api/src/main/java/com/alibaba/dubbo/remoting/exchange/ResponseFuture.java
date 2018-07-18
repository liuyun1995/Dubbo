package com.alibaba.dubbo.remoting.exchange;

import com.alibaba.dubbo.remoting.RemotingException;

//响应Future
public interface ResponseFuture {

    //获得值
    Object get() throws RemotingException;

    //获得值
    Object get(int timeoutInMillis) throws RemotingException;

    //设置回调
    void setCallback(ResponseCallback callback);

    //是否完成
    boolean isDone();

}