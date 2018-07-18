package com.alibaba.dubbo.remoting.http.servlet;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.http.HttpHandler;
import com.alibaba.dubbo.remoting.http.support.AbstractHttpServer;

/**
 * 基于 Servlet 的服务器实现类
 */
public class ServletHttpServer extends AbstractHttpServer {

    public ServletHttpServer(URL url, HttpHandler handler) {
        super(url, handler);

        // 注册 HttpHandler 到 DispatcherServlet 中
        DispatcherServlet.addHttpHandler(url.getParameter(Constants.BIND_PORT_KEY, 8080), handler);
    }

}