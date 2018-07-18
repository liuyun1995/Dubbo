package com.alibaba.dubbo.remoting.http.servlet;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.remoting.http.HttpBinder;
import com.alibaba.dubbo.remoting.http.HttpHandler;
import com.alibaba.dubbo.remoting.http.HttpServer;

/**
 * ServletHttpTransporter
 */
public class ServletHttpBinder implements HttpBinder {

    @Adaptive()
    public HttpServer bind(URL url, HttpHandler handler) {
        return new ServletHttpServer(url, handler);
    }

}