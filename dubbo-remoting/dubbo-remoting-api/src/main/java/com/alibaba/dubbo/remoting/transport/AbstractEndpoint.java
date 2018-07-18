package com.alibaba.dubbo.remoting.transport;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.Resetable;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.remoting.ChannelHandler;
import com.alibaba.dubbo.remoting.Codec;
import com.alibaba.dubbo.remoting.Codec2;
import com.alibaba.dubbo.remoting.transport.codec.CodecAdapter;

//抽象终端
public abstract class AbstractEndpoint extends AbstractPeer implements Resetable {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEndpoint.class);
    private Codec2 codec;        //编解码器
    private int timeout;         //超时时间
    private int connectTimeout;  //连接超时时间

    //构造器
    public AbstractEndpoint(URL url, ChannelHandler handler) {
        super(url, handler);
        this.codec = getChannelCodec(url);
        this.timeout = url.getPositiveParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT);
        this.connectTimeout = url.getPositiveParameter(Constants.CONNECT_TIMEOUT_KEY, Constants.DEFAULT_CONNECT_TIMEOUT);
    }

    //获得编解码器
    protected static Codec2 getChannelCodec(URL url) {
        String codecName = url.getParameter(Constants.CODEC_KEY, "telnet");
        if (ExtensionLoader.getExtensionLoader(Codec2.class).hasExtension(codecName)) { // 例如，在 DubboProtocol 中，会获得 DubboCodec
            return ExtensionLoader.getExtensionLoader(Codec2.class).getExtension(codecName);
        } else {
            return new CodecAdapter(ExtensionLoader.getExtensionLoader(Codec.class).getExtension(codecName));
        }
    }

    @Override
    public void reset(URL url) {
        if (isClosed()) {
            throw new IllegalStateException("Failed to reset parameters "
                    + url + ", cause: Channel closed. channel: " + getLocalAddress());
        }
        // 重置 `timeout` 属性
        try {
            if (url.hasParameter(Constants.TIMEOUT_KEY)) {
                int t = url.getParameter(Constants.TIMEOUT_KEY, 0);
                if (t > 0) {
                    this.timeout = t;
                }
            }
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
        // 重置 `connectTimeout` 属性
        try {
            if (url.hasParameter(Constants.CONNECT_TIMEOUT_KEY)) {
                int t = url.getParameter(Constants.CONNECT_TIMEOUT_KEY, 0);
                if (t > 0) {
                    this.connectTimeout = t;
                }
            }
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
        // 重置 `codec` 属性
        try {
            if (url.hasParameter(Constants.CODEC_KEY)) {
                this.codec = getChannelCodec(url);
            }
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
    }

    @Deprecated
    public void reset(com.alibaba.dubbo.common.Parameters parameters) {
        reset(getUrl().addParameters(parameters.getParameters()));
    }

    protected Codec2 getCodec() {
        return codec;
    }

    protected int getTimeout() {
        return timeout;
    }

    protected int getConnectTimeout() {
        return connectTimeout;
    }

}