package com.alibaba.dubbo.rpc.protocol.rest;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;

import org.jboss.resteasy.spi.ResteasyDeployment;

/**
 * Rest Server 抽象基类
 */
public abstract class BaseRestServer implements RestServer {

    @Override
    public void start(URL url) {
        // 添加 MediaType
        getDeployment().getMediaTypeMappings().put("json", "application/json");
        getDeployment().getMediaTypeMappings().put("xml", "text/xml");
//        server.getDeployment().getMediaTypeMappings().put("xml", "application/xml");
        // 添加过滤器 RpcContextFilter
        getDeployment().getProviderClasses().add(RpcContextFilter.class.getName());
        // TODO users can override this mapper, but we just rely on the current priority strategy of resteasy
        // 添加异常匹配 RpcExceptionMapper
        getDeployment().getProviderClasses().add(RpcExceptionMapper.class.getName());
        // 从 `extension` 配置项，添加对应的组件（过滤器 Filter 、拦截器 Interceptor 、异常匹配器 ExceptionMapper 等等）
        loadProviders(url.getParameter(Constants.EXTENSION_KEY, ""));

        // 启动服务器
        doStart(url);
    }

    @Override
    public void deploy(Class resourceDef, Object resourceInstance, String contextPath) {
        if (StringUtils.isEmpty(contextPath)) {
            getDeployment().getRegistry().addResourceFactory(new DubboResourceFactory(resourceInstance, resourceDef));
        } else {
            getDeployment().getRegistry().addResourceFactory(new DubboResourceFactory(resourceInstance, resourceDef), contextPath);
        }
    }

    @Override
    public void undeploy(Class resourceDef) {
        getDeployment().getRegistry().removeRegistrations(resourceDef);
    }

    protected void loadProviders(String value) {
        for (String clazz : Constants.COMMA_SPLIT_PATTERN.split(value)) {
            if (!StringUtils.isEmpty(clazz)) {
                getDeployment().getProviderClasses().add(clazz.trim());
            }
        }
    }

    protected abstract ResteasyDeployment getDeployment();

    protected abstract void doStart(URL url);

}
