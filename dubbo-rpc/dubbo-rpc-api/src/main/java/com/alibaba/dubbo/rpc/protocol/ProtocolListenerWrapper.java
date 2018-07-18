package com.alibaba.dubbo.rpc.protocol;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.listener.ListenerExporterWrapper;
import com.alibaba.dubbo.rpc.listener.ListenerInvokerWrapper;

import java.util.Collections;
import java.util.List;

/**
 * ListenerProtocol
 *
 * 用于给 Exporter 增加监听器。
 */
public class ProtocolListenerWrapper implements Protocol {

    private final Protocol protocol;

    public ProtocolListenerWrapper(Protocol protocol) {
        if (protocol == null) {
            throw new IllegalArgumentException("protocol == null");
        }
        this.protocol = protocol;
    }

    public int getDefaultPort() {
        return protocol.getDefaultPort();
    }

    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        // 注册中心协议
        if (Constants.REGISTRY_PROTOCOL.equals(invoker.getUrl().getProtocol())) {
            return protocol.export(invoker);
        }
        // 暴露服务，创建 Exporter 对象
        Exporter<T> exporter = protocol.export(invoker);
        // 获得 ExporterListener 数组
        List<ExporterListener> listeners = Collections.unmodifiableList(ExtensionLoader.getExtensionLoader(ExporterListener.class).getActivateExtension(invoker.getUrl(), Constants.EXPORTER_LISTENER_KEY));
        // 创建带 ExporterListener 的 Exporter 对象
        return new ListenerExporterWrapper<T>(exporter, listeners);
    }

    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        // 注册中心协议
        if (Constants.REGISTRY_PROTOCOL.equals(url.getProtocol())) {
            return protocol.refer(type, url);
        }
        // 引用服务
        Invoker<T> invoker = protocol.refer(type, url);
        // 获得 InvokerListener 数组
        List<InvokerListener> listeners = Collections.unmodifiableList(ExtensionLoader.getExtensionLoader(InvokerListener.class).getActivateExtension(url, Constants.INVOKER_LISTENER_KEY));
        // 创建 ListenerInvokerWrapper 对象
        return new ListenerInvokerWrapper<T>(invoker, listeners);
    }

    @Override
    public void destroy() {
        protocol.destroy();
    }

}