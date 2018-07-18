package com.alibaba.dubbo.container.spring;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.Container;

import org.springframework.context.support.ClassPathXmlApplicationContext;

//Spring容器实现类
public class SpringContainer implements Container {

    private static final Logger logger = LoggerFactory.getLogger(SpringContainer.class);
    //Spring配置属性
    public static final String SPRING_CONFIG = "dubbo.spring.config";
    //默认配置文件地址
    public static final String DEFAULT_SPRING_CONFIG = "classpath*:META-INF/spring/*.xml";
    //Spring应用上下文
    static ClassPathXmlApplicationContext context;

    //获取Spring应用上下文
    public static ClassPathXmlApplicationContext getContext() {
        return context;
    }

    //开启应用
    @Override
    public void start() {
        //获取Spring配置文件地址
        String configPath = ConfigUtils.getProperty(SPRING_CONFIG);
        if (configPath == null || configPath.length() == 0) {
            configPath = DEFAULT_SPRING_CONFIG;
        }
        //创建Spring应用上下文
        context = new ClassPathXmlApplicationContext(configPath.split("[,\\s]+"));
        //启动Spring容器
        context.start();
    }

    //停止应用
    @Override
    public void stop() {
        try {
            if (context != null) {
                //停止Spring容器
                context.stop();
                //关闭Spring容器
                context.close();
                context = null;
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

}