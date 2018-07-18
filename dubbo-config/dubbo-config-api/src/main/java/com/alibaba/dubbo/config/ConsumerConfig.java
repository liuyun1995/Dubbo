package com.alibaba.dubbo.config;

//服务消费者缺省值配置
public class ConsumerConfig extends AbstractReferenceConfig {

    private static final long serialVersionUID = 2827274711143680600L;

    // is default or not
    private Boolean isDefault;

    // networking framework client uses: netty, mina, etc.
    private String client; // TODO 芋艿

    @Override
    public void setTimeout(Integer timeout) {
        super.setTimeout(timeout);
        String rmiTimeout = System.getProperty("sun.rmi.transport.tcp.responseTimeout");
        if (timeout != null && timeout > 0
                && (rmiTimeout == null || rmiTimeout.length() == 0)) {
            System.setProperty("sun.rmi.transport.tcp.responseTimeout", String.valueOf(timeout));
        }
    }

    public Boolean isDefault() {
        return isDefault;
    }

    public void setDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}