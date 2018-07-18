package com.alibaba.dubbo.remoting.transport.codec;

import com.alibaba.dubbo.common.io.UnsafeByteArrayInputStream;
import com.alibaba.dubbo.common.io.UnsafeByteArrayOutputStream;
import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.dubbo.remoting.Channel;
import com.alibaba.dubbo.remoting.Codec;
import com.alibaba.dubbo.remoting.Codec2;
import com.alibaba.dubbo.remoting.buffer.ChannelBuffer;

import java.io.IOException;

/**
 * Codec适配器，将 Codec 适配成 Codec2
 */
public class CodecAdapter implements Codec2 {

    /**
     * codec
     */
    private Codec codec;

    public CodecAdapter(Codec codec) {
        Assert.notNull(codec, "codec == null");
        this.codec = codec;
    }

    @Override
    public void encode(Channel channel, ChannelBuffer buffer, Object message)
            throws IOException {
        UnsafeByteArrayOutputStream os = new UnsafeByteArrayOutputStream(1024);
        // 编码
        codec.encode(channel, os, message);
        // 写入 buffer
        buffer.writeBytes(os.toByteArray());
    }

    @Override
    public Object decode(Channel channel, ChannelBuffer buffer) throws IOException {
        // 读取字节到数组
        byte[] bytes = new byte[buffer.readableBytes()];
        int savedReaderIndex = buffer.readerIndex();
        buffer.readBytes(bytes);
        // 解码
        UnsafeByteArrayInputStream is = new UnsafeByteArrayInputStream(bytes);
        Object result = codec.decode(channel, is);
        // 设置最新的开始读取位置
        buffer.readerIndex(savedReaderIndex + is.position());
        // 返回是否要进一步读取
        return result == Codec.NEED_MORE_INPUT ? DecodeResult.NEED_MORE_INPUT : result;
    }

    public Codec getCodec() {
        return codec;
    }

}
