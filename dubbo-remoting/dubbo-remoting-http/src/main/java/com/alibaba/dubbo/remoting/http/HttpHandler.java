package com.alibaba.dubbo.remoting.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//HTTP处理器接口
public interface HttpHandler {

    /**
     * invoke.
     *
     * 处理器请求
     *
     * @param request  request. 请求
     * @param response response. 响应
     * @throws IOException 当 IO 发生异常
     * @throws ServletException 当 Servlet 发生异常
     */
    void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

}