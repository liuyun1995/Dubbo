package com.alibaba.dubbo.remoting.http.servlet;

import com.alibaba.dubbo.remoting.http.HttpHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service dispatcher Servlet.
 *
 * 服务调度 Servlet
 */
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 5766349180380479888L;

    /**
     * 处理器集合
     *
     * key：服务器端口
     */
    private static final Map<Integer, HttpHandler> handlers = new ConcurrentHashMap<Integer, HttpHandler>();
    /**
     * 单例
     */
    private static DispatcherServlet INSTANCE;

    public DispatcherServlet() {
        DispatcherServlet.INSTANCE = this;
    }

    public static DispatcherServlet getInstance() {
        return INSTANCE;
    }

    /**
     * 添加处理器
     *
     * @param port 服务器端口
     * @param processor 处理器
     */
    public static void addHttpHandler(int port, HttpHandler processor) {
        handlers.put(port, processor);
    }

    /**
     * 移除处理器
     *
     * @param port 服务器端口
     */
    public static void removeHttpHandler(int port) {
        handlers.remove(port);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获得处理器
        HttpHandler handler = handlers.get(request.getLocalPort());
        // 处理器不存在，报错
        if (handler == null) {// service not found.
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Service not found.");
        // 处理请求
        } else {
            handler.handle(request, response);
        }
    }

}