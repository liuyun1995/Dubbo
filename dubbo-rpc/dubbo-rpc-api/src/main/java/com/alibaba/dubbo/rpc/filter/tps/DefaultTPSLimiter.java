package com.alibaba.dubbo.rpc.filter.tps;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 默认 TPS 限制器实现类
 */
public class DefaultTPSLimiter implements TPSLimiter {

    /**
     * StatItem 集合
     *
     * key：服务名
     */
    private final ConcurrentMap<String, StatItem> stats = new ConcurrentHashMap<String, StatItem>();

    @Override
    public boolean isAllowable(URL url, Invocation invocation) {
        // 获得 TPS 大小配置项
        int rate = url.getParameter(Constants.TPS_LIMIT_RATE_KEY, -1);
        // 获得 TPS 周期配置项，默认 60 秒
        long interval = url.getParameter(Constants.TPS_LIMIT_INTERVAL_KEY, Constants.DEFAULT_TPS_LIMIT_INTERVAL);
        String serviceKey = url.getServiceKey();
        // 要限流
        if (rate > 0) {
            // 获得 StatItem 对象
            StatItem statItem = stats.get(serviceKey);
            // 不存在，则进行创建
            if (statItem == null) {
                stats.putIfAbsent(serviceKey, new StatItem(serviceKey, rate, interval));
                statItem = stats.get(serviceKey);
            }
            // 根据 TPS 限流规则判断是否限制此次调用.
            return statItem.isAllowable(url, invocation);
        // 不限流
        } else {
            // 移除 StatItem
            StatItem statItem = stats.get(serviceKey);
            if (statItem != null) {
                stats.remove(serviceKey);
            }
            // 返回通过
            return true;
        }
    }

}
