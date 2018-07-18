package com.alibaba.dubbo.remoting;

import java.net.InetSocketAddress;

//超时异常
public class TimeoutException extends RemotingException {
    private static final long serialVersionUID = 3122966731958222692L;
    public static final int CLIENT_SIDE = 0;   //客户端
    public static final int SERVER_SIDE = 1;   //服务端
    private final int phase;                   //阶段

    public TimeoutException(boolean serverSide, Channel channel, String message) {
        super(channel, message);
        this.phase = serverSide ? SERVER_SIDE : CLIENT_SIDE;
    }

    public TimeoutException(boolean serverSide, InetSocketAddress localAddress,
                            InetSocketAddress remoteAddress, String message) {
        super(localAddress, remoteAddress, message);
        this.phase = serverSide ? SERVER_SIDE : CLIENT_SIDE;
    }

    public int getPhase() {
        return phase;
    }

    public boolean isServerSide() {
        return phase == 1;
    }

    public boolean isClientSide() {
        return phase == 0;
    }

}