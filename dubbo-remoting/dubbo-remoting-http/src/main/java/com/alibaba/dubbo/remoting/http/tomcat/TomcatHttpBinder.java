package com.alibaba.dubbo.remoting.http.tomcat;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.http.HttpBinder;
import com.alibaba.dubbo.remoting.http.HttpHandler;
import com.alibaba.dubbo.remoting.http.HttpServer;

/**
 * TomcatHttpServer 绑定器实现类
 */
public class TomcatHttpBinder implements HttpBinder {

    @Override
    public HttpServer bind(URL url, HttpHandler handler) {
        return new TomcatHttpServer(url, handler);
    }

}