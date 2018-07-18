package com.alibaba.dubbo.rpc.cluster.merger;

import com.alibaba.dubbo.rpc.cluster.Merger;

public class DoubleArrayMerger implements Merger<double[]> {

    @Override
    public double[] merge(double[]... items) {
        int total = 0;
        for (double[] array : items) {
            total += array.length;
        }
        double[] result = new double[total];
        int index = 0;
        for (double[] array : items) {
            for (double item : array) {
                result[index++] = item;
            }
        }
        return result;
    }
}
