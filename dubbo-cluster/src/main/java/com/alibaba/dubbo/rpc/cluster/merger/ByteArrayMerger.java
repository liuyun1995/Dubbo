package com.alibaba.dubbo.rpc.cluster.merger;

import com.alibaba.dubbo.rpc.cluster.Merger;

public class ByteArrayMerger implements Merger<byte[]> {

    @Override
    public byte[] merge(byte[]... items) {
        int total = 0;
        for (byte[] array : items) {
            total += array.length;
        }
        byte[] result = new byte[total];
        int index = 0;
        for (byte[] array : items) {
            for (byte item : array) {
                result[index++] = item;
            }
        }
        return result;
    }

}
