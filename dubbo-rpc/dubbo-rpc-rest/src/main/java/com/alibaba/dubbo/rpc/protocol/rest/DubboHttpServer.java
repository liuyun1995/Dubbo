package com.alibaba.dubbo.rpc.protocol.rest;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.http.HttpBinder;
import com.alibaba.dubbo.remoting.http.HttpHandler;
import com.alibaba.dubbo.remoting.http.HttpServer;
import com.alibaba.dubbo.remoting.http.servlet.BootstrapListener;
import com.alibaba.dubbo.remoting.http.servlet.ServletManager;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 基于 `dubbo-remoting-http` 的 HTTP 服务器实现类
 */
public class DubboHttpServer extends BaseRestServer {

    /**
     * Resteasy HttpServletDispatcher
     */
    private final HttpServletDispatcher dispatcher = new HttpServletDispatcher();
    /**
     * Resteasy ResteasyDeployment
     */
    private final ResteasyDeployment deployment = new ResteasyDeployment();
    /**
     * Dubbo HttpBinder$Adaptive
     */
    private HttpBinder httpBinder;
    /**
     * HttpServer 对象
     */
    private HttpServer httpServer;
//    private boolean isExternalServer;

    public DubboHttpServer(HttpBinder httpBinder) {
        this.httpBinder = httpBinder;
    }

    @Override
    protected void doStart(URL url) {
        // 创建 HttpServer 对象，使用 RestHandler 作为处理器。
        // TODO jetty will by default enable keepAlive so the xml config has no effect now
        httpServer = httpBinder.bind(url, new RestHandler());

        // 获得 ServletContext 对象
        ServletContext servletContext = ServletManager.getInstance().getServletContext(url.getPort());
        if (servletContext == null) {
            servletContext = ServletManager.getInstance().getServletContext(ServletManager.EXTERNAL_SERVER_PORT);
        }
        if (servletContext == null) {
            throw new RpcException("No servlet context found. If you are using server='servlet', " +
                    "make sure that you've configured " + BootstrapListener.class.getName() + " in web.xml");
        }
        // 设置 ResteasyDeployment
        servletContext.setAttribute(ResteasyDeployment.class.getName(), deployment); // https://github.com/resteasy/Resteasy/blob/master/server-adapters/resteasy-undertow/src/main/java/org/jboss/resteasy/plugins/server/undertow/UndertowJaxrsServer.java#L74

        // 初始化 Resteasy HttpServletDispatcher
        try {
            dispatcher.init(new SimpleServletConfig(servletContext));
        } catch (ServletException e) {
            throw new RpcException(e);
        }
    }

    @Override
    public void stop() {
        httpServer.close();
    }

    @Override
    protected ResteasyDeployment getDeployment() {
        return deployment;
    }

    private class RestHandler implements HttpHandler {

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            // 设置
            RpcContext.getContext().setRemoteAddress(request.getRemoteAddr(), request.getRemotePort());
            // 调度请求
            dispatcher.service(request, response);
        }

    }

    private static class SimpleServletConfig implements ServletConfig {

        private final ServletContext servletContext;

        public SimpleServletConfig(ServletContext servletContext) {
            this.servletContext = servletContext;
        }

        @Override
        public String getServletName() {
            return "DispatcherServlet";
        }

        @Override
        public ServletContext getServletContext() {
            return servletContext;
        }

        @Override
        public String getInitParameter(String s) {
            return null;
        }

        @Override
        public Enumeration getInitParameterNames() {
            return new Enumeration() {

                @Override
                public boolean hasMoreElements() {
                    return false;
                }

                @Override
                public Object nextElement() {
                    return null;
                }

            };
        }

    }

}
