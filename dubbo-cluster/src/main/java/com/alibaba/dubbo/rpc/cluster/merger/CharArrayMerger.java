package com.alibaba.dubbo.rpc.cluster.merger;

import com.alibaba.dubbo.rpc.cluster.Merger;

public class CharArrayMerger implements Merger<char[]> {

    @Override
    public char[] merge(char[]... items) {
        int total = 0;
        for (char[] array : items) {
            total += array.length;
        }
        char[] result = new char[total];
        int index = 0;
        for (char[] array : items) {
            for (char item : array) {
                result[index++] = item;
            }
        }
        return result;
    }
}
