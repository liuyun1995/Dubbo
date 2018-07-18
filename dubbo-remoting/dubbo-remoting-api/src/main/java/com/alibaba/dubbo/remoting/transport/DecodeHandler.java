package com.alibaba.dubbo.remoting.transport;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.remoting.Channel;
import com.alibaba.dubbo.remoting.ChannelHandler;
import com.alibaba.dubbo.remoting.Decodeable;
import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.exchange.Request;
import com.alibaba.dubbo.remoting.exchange.Response;

//解码处理器
public class DecodeHandler extends AbstractChannelHandlerDelegate {

    private static final Logger log = LoggerFactory.getLogger(DecodeHandler.class);

    public DecodeHandler(ChannelHandler handler) {
        super(handler);
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        if (message instanceof Decodeable) {
            decode(message);
        }
        if (message instanceof Request) {
            decode(((Request) message).getData());
        }
        if (message instanceof Response) {
            decode(((Response) message).getResult());
        }
        handler.received(channel, message);
    }

    private void decode(Object message) {
        if (message != null && message instanceof Decodeable) {
            try {
                //解析消息
                ((Decodeable) message).decode();
                if (log.isDebugEnabled()) {
                    log.debug(new StringBuilder(32).append("Decode decodeable message ").append(message.getClass().getName()).toString());
                }
            } catch (Throwable e) {
                if (log.isWarnEnabled()) {
                    log.warn(new StringBuilder(32).append("Call Decodeable.decode failed: ").append(e.getMessage()).toString(), e);
                }
            }
        }
    }

}
