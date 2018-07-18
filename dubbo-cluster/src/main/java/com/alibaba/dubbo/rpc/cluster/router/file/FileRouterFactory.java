package com.alibaba.dubbo.rpc.cluster.router.file;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.IOUtils;
import com.alibaba.dubbo.rpc.cluster.Router;
import com.alibaba.dubbo.rpc.cluster.RouterFactory;
import com.alibaba.dubbo.rpc.cluster.router.script.ScriptRouterFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 基于文件读取路由规则，创建对应的 Router 实现类的对象
 */
public class FileRouterFactory implements RouterFactory {

    public static final String NAME = "file";

    /**
     * RouterFactory$Adaptive 对象
     */
    private RouterFactory routerFactory;

    public void setRouterFactory(RouterFactory routerFactory) {
        this.routerFactory = routerFactory;
    }

    @Override
    public Router getRouter(URL url) {
        try {
            // Transform File URL into Script Route URL, and Load
            // file:///d:/path/to/route.js?router=script ==> script:///d:/path/to/route.js?type=js&rule=<file-content>
            // 获得 router 配置项，默认为 script
            String protocol = url.getParameter(Constants.ROUTER_KEY, ScriptRouterFactory.NAME); // Replace original protocol (maybe 'file') with 'script'
            // 使用文件后缀做为类型
            String type = null; // Use file suffix to config script type, e.g., js, groovy ...
            String path = url.getPath();
            if (path != null) {
                int i = path.lastIndexOf('.');
                if (i > 0) {
                    type = path.substring(i + 1);
                }
            }
            // 读取规则内容
            String rule = IOUtils.read(new FileReader(new File(url.getAbsolutePath())));

            // 创建路由规则 URL
            boolean runtime = url.getParameter(Constants.RUNTIME_KEY, false);
            URL script = url.setProtocol(protocol).addParameter(Constants.TYPE_KEY, type)
                    .addParameter(Constants.RUNTIME_KEY, runtime)
                    .addParameterAndEncoded(Constants.RULE_KEY, rule);

            // 通过 Dubbo SPI Adaptive 机制，获得 Router 对象
            return routerFactory.getRouter(script);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}