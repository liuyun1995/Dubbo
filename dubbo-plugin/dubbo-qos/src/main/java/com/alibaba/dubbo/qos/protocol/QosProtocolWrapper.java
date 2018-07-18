package com.alibaba.dubbo.qos.protocol;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.qos.server.Server;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.RpcException;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.alibaba.dubbo.common.Constants.ACCEPT_FOREIGN_IP;
import static com.alibaba.dubbo.common.Constants.QOS_ENABLE;
import static com.alibaba.dubbo.common.Constants.QOS_PORT;

public class QosProtocolWrapper implements Protocol {
    private static AtomicBoolean hasStarted = new AtomicBoolean(false);

    private Protocol protocol;

    public QosProtocolWrapper(Protocol protocol) {
        if (protocol == null) {
            throw new IllegalArgumentException("protocol == null");
        }
        this.protocol = protocol;
    }

    @Override
    public int getDefaultPort() {
        return protocol.getDefaultPort();
    }

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        if (Constants.REGISTRY_PROTOCOL.equals(invoker.getUrl().getProtocol())) {
            startQosServer(invoker.getUrl());
            return protocol.export(invoker);
        }
        return protocol.export(invoker);
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        if (Constants.REGISTRY_PROTOCOL.equals(url.getProtocol())) {
            startQosServer(url);
            return protocol.refer(type, url);
        }
        return protocol.refer(type, url);
    }

    @Override
    public void destroy() {
        protocol.destroy();
    }

    private void startQosServer(URL url) {
        if (!hasStarted.compareAndSet(false, true)) {
            return;
        }

        try {
            boolean qosEnable = Boolean.parseBoolean(url.getParameter(QOS_ENABLE,"true"));
            if (!qosEnable) {
                return;
            }

            int port = Integer.parseInt(url.getParameter(QOS_PORT,"22222"));
            boolean acceptForeignIp = Boolean.parseBoolean(url.getParameter(ACCEPT_FOREIGN_IP,"true"));
            Server server = com.alibaba.dubbo.qos.server.Server.getInstance();
            server.setPort(port);
            server.setAcceptForeignIp(acceptForeignIp);
            server.start();

        } catch (Throwable throwable) {
            //throw new RpcException("fail to start qos server", throwable);
        }
    }
}
