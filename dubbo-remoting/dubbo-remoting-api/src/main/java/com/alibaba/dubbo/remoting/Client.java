package com.alibaba.dubbo.remoting;

import com.alibaba.dubbo.common.Resetable;

//客户端接口
public interface Client extends Endpoint, Channel, Resetable {

    //重连
    void reconnect() throws RemotingException;

    //重置
    @Deprecated
    void reset(com.alibaba.dubbo.common.Parameters parameters);

}