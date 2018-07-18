package com.alibaba.dubbo.registry.redis;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.support.AbstractRegistryFactory;

//Redis注册中心工厂
public class RedisRegistryFactory extends AbstractRegistryFactory {

    @Override
    protected Registry createRegistry(URL url) {
        return new RedisRegistry(url);
    }

}
