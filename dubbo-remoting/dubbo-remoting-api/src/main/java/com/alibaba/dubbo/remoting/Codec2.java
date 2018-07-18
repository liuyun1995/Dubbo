package com.alibaba.dubbo.remoting;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;
import com.alibaba.dubbo.remoting.buffer.ChannelBuffer;

import java.io.IOException;

//编解码器接口
@SPI
public interface Codec2 {

    //编码
    @Adaptive({Constants.CODEC_KEY})
    void encode(Channel channel, ChannelBuffer buffer, Object message) throws IOException;

    //解码
    @Adaptive({Constants.CODEC_KEY})
    Object decode(Channel channel, ChannelBuffer buffer) throws IOException;

    //解码结果
    enum DecodeResult {
        NEED_MORE_INPUT,   //需要更多输入
        SKIP_SOME_INPUT    //忽略一些输入
    }

}

