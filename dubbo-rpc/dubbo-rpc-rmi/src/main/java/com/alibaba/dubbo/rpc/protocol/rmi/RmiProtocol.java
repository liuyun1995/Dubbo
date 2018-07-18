package com.alibaba.dubbo.rpc.protocol.rmi;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.Version;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.AbstractProxyProtocol;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.rmi.RemoteException;

//RMI协议实现类
public class RmiProtocol extends AbstractProxyProtocol {

    //默认端口
    public static final int DEFAULT_PORT = 1099;

    public RmiProtocol() {
        super(RemoteAccessException.class, RemoteException.class);
    }

    public int getDefaultPort() {
        return DEFAULT_PORT;
    }

    @Override
    protected <T> Runnable doExport(final T impl, Class<T> type, URL url) throws RpcException {
        // 创建 RmiServiceExporter 对象
        final RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setRegistryPort(url.getPort());
        rmiServiceExporter.setServiceName(url.getPath());
        rmiServiceExporter.setServiceInterface(type);
        rmiServiceExporter.setService(impl);
        try {
            rmiServiceExporter.afterPropertiesSet();
        } catch (RemoteException e) {
            throw new RpcException(e.getMessage(), e);
        }
        // 返回取消暴露的回调 Runnable
        return new Runnable() {
            public void run() {
                try {
                    rmiServiceExporter.destroy();
                } catch (Throwable e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T doRefer(final Class<T> serviceType, final URL url) throws RpcException {
        // 创建 RmiProxyFactoryBean 对象
        final RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        // RMI needs extra parameter since it uses customized remote invocation object
        // RMI传输时使用自定义的远程执行对象，从而传递额外的参数
        if (url.getParameter(Constants.DUBBO_VERSION_KEY, Version.getVersion()).equals(Version.getVersion())) {
            // Check dubbo version on provider, this feature only support
            rmiProxyFactoryBean.setRemoteInvocationFactory(new RemoteInvocationFactory() {
                public RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation) {
                    return new RmiRemoteInvocation(methodInvocation);
                }
            });
        }
        // 设置相关参数
        rmiProxyFactoryBean.setServiceUrl(url.toIdentityString());
        rmiProxyFactoryBean.setServiceInterface(serviceType);
        rmiProxyFactoryBean.setCacheStub(true);
        rmiProxyFactoryBean.setLookupStubOnStartup(true);
        rmiProxyFactoryBean.setRefreshStubOnConnectFailure(true);
        rmiProxyFactoryBean.afterPropertiesSet();
        // 创建 Service Proxy 对象
        return (T) rmiProxyFactoryBean.getObject();
    }

    @Override
    protected int getErrorCode(Throwable e) {
        if (e instanceof RemoteAccessException) {
            e = e.getCause();
        }
        if (e != null && e.getCause() != null) {
            Class<?> cls = e.getCause().getClass();
            if (SocketTimeoutException.class.equals(cls)) {
                return RpcException.TIMEOUT_EXCEPTION;
            } else if (IOException.class.isAssignableFrom(cls)) {
                return RpcException.NETWORK_EXCEPTION;
            } else if (ClassNotFoundException.class.isAssignableFrom(cls)) {
                return RpcException.SERIALIZATION_EXCEPTION;
            }
        }
        return super.getErrorCode(e);
    }

}