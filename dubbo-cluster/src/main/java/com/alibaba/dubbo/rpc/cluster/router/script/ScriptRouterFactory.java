package com.alibaba.dubbo.rpc.cluster.router.script;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.cluster.Router;
import com.alibaba.dubbo.rpc.cluster.RouterFactory;

public class ScriptRouterFactory implements RouterFactory {

    public static final String NAME = "script";

    @Override
    public Router getRouter(URL url) {
        return new ScriptRouter(url);
    }

}