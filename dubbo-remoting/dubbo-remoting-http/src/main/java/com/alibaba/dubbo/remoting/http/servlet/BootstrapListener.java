package com.alibaba.dubbo.remoting.http.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This class must be defined before something like spring's ContextLoaderListener in web.xml
 *
 */
public class BootstrapListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletManager.getInstance().addServletContext(ServletManager.EXTERNAL_SERVER_PORT, servletContextEvent.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletManager.getInstance().removeServletContext(ServletManager.EXTERNAL_SERVER_PORT);
    }

}
