package com.alibaba.dubbo.registry.zookeeper;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.support.AbstractRegistryFactory;
import com.alibaba.dubbo.remoting.zookeeper.ZookeeperTransporter;

//Zookeeper注册中心工厂
public class ZookeeperRegistryFactory extends AbstractRegistryFactory {

    /**
     * Zookeeper 工厂
     */
    private ZookeeperTransporter zookeeperTransporter;

    /**
     * 设置 Zookeeper 工厂
     *
     * 该方法，通过 Dubbo SPI 注入
     *
     * @param zookeeperTransporter Zookeeper 工厂对象
     */
    public void setZookeeperTransporter(ZookeeperTransporter zookeeperTransporter) {
        this.zookeeperTransporter = zookeeperTransporter;
    }

    @Override
    public Registry createRegistry(URL url) {
        return new ZookeeperRegistry(url, zookeeperTransporter);
    }

}