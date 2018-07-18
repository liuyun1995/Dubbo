package com.alibaba.dubbo.remoting.http.servlet;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//Servlet管理器
public class ServletManager {

    /**
     * 外部服务器端口，用于 `servlet` 的服务器端口
     */
    public static final int EXTERNAL_SERVER_PORT = -1234;

    /**
     * 单例
     */
    private static final ServletManager instance = new ServletManager();
    /**
     * ServletContext 集合
     */
    private final Map<Integer, ServletContext> contextMap = new ConcurrentHashMap<Integer, ServletContext>();

    public static ServletManager getInstance() {
        return instance;
    }

    /**
     * 添加 ServletContext 对象
     *
     * @param port 服务器端口
     * @param servletContext ServletContext 对象
     */
    public void addServletContext(int port, ServletContext servletContext) {
        contextMap.put(port, servletContext);
    }

    /**
     * 移除 ServletContext 对象
     *
     * @param port 服务器端口
     */
    public void removeServletContext(int port) {
        contextMap.remove(port);
    }

    /**
     * 获得 ServletContext 对象
     *
     * @param port 服务器端口
     * @return ServletContext 对象
     */
    public ServletContext getServletContext(int port) {
        return contextMap.get(port);
    }

}
