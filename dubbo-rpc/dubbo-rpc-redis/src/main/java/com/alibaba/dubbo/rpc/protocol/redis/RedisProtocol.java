package com.alibaba.dubbo.rpc.protocol.redis;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.Serialization;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.dubbo.rpc.protocol.AbstractInvoker;
import com.alibaba.dubbo.rpc.protocol.AbstractProtocol;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

//Redis协议
public class RedisProtocol extends AbstractProtocol {

    public static final int DEFAULT_PORT = 6379;

    public int getDefaultPort() {
        return DEFAULT_PORT;
    }

    @Override
    public <T> Exporter<T> export(final Invoker<T> invoker) throws RpcException {
        throw new UnsupportedOperationException("Unsupported export redis service. url: " + invoker.getUrl());
    }

    private Serialization getSerialization(URL url) {
        return ExtensionLoader.getExtensionLoader(Serialization.class).getExtension(url.getParameter(Constants.SERIALIZATION_KEY, "java"));
    }

    @Override
    public <T> Invoker<T> refer(final Class<T> type, final URL url) throws RpcException {
        try {
            // 创建 GenericObjectPoolConfig 对象，设置配置
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setTestOnBorrow(url.getParameter("test.on.borrow", true));
            config.setTestOnReturn(url.getParameter("test.on.return", false));
            config.setTestWhileIdle(url.getParameter("test.while.idle", false));
            if (url.getParameter("max.idle", 0) > 0)
                config.setMaxIdle(url.getParameter("max.idle", 0));
            if (url.getParameter("min.idle", 0) > 0)
                config.setMinIdle(url.getParameter("min.idle", 0));
            if (url.getParameter("max.active", 0) > 0)
                config.setMaxTotal(url.getParameter("max.active", 0));
            if (url.getParameter("max.total", 0) > 0)
                config.setMaxTotal(url.getParameter("max.total", 0));
            if (url.getParameter("max.wait", 0) > 0)
                config.setMaxWaitMillis(url.getParameter("max.wait", 0));
            if (url.getParameter("num.tests.per.eviction.run", 0) > 0)
                config.setNumTestsPerEvictionRun(url.getParameter("num.tests.per.eviction.run", 0));
            if (url.getParameter("time.between.eviction.runs.millis", 0) > 0)
                config.setTimeBetweenEvictionRunsMillis(url.getParameter("time.between.eviction.runs.millis", 0));
            if (url.getParameter("min.evictable.idle.time.millis", 0) > 0)
                config.setMinEvictableIdleTimeMillis(url.getParameter("min.evictable.idle.time.millis", 0));
            // 创建 JedisPool 对象
            final JedisPool jedisPool = new JedisPool(config, url.getHost(), url.getPort(DEFAULT_PORT),
                    url.getParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT));

            // 处理方法名的映射
            final int expiry = url.getParameter("expiry", 0);
            final String get = url.getParameter("get", "get");
            final String set = url.getParameter("set", Map.class.equals(type) ? "put" : "set");
            final String delete = url.getParameter("delete", Map.class.equals(type) ? "remove" : "delete");

            // 创建 Invoker 对象
            return new AbstractInvoker<T>(type, url) {

                @Override
                protected Result doInvoke(Invocation invocation) {
                    Jedis resource = null;
                    try {
                        // 获得 Redis Resource
                        resource = jedisPool.getResource();
                        // Redis get 指令
                        if (get.equals(invocation.getMethodName())) {
                            if (invocation.getArguments().length != 1) {
                                throw new IllegalArgumentException("The redis get method arguments mismatch, must only one arguments. interface: " + type.getName() + ", method: " + invocation.getMethodName() + ", url: " + url);
                            }
                            // 获得值
                            byte[] value = resource.get(String.valueOf(invocation.getArguments()[0]).getBytes());
                            if (value == null) {
                                return new RpcResult();
                            }
                            // 反序列化
                            ObjectInput oin = getSerialization(url).deserialize(url, new ByteArrayInputStream(value));
                            // 返回结果
                            return new RpcResult(oin.readObject());
                        // Redis set/put 指令
                        } else if (set.equals(invocation.getMethodName())) {
                            if (invocation.getArguments().length != 2) {
                                throw new IllegalArgumentException("The redis set method arguments mismatch, must be two arguments. interface: " + type.getName() + ", method: " + invocation.getMethodName() + ", url: " + url);
                            }
                            // 序列化
                            byte[] key = String.valueOf(invocation.getArguments()[0]).getBytes();
                            ByteArrayOutputStream output = new ByteArrayOutputStream();
                            ObjectOutput value = getSerialization(url).serialize(url, output);
                            value.writeObject(invocation.getArguments()[1]);
                            // 设置值
                            resource.set(key, output.toByteArray());
                            if (expiry > 1000) {
                                resource.expire(key, expiry / 1000);
                            }
                            // 返回结果
                            return new RpcResult();
                        // Redis remote/delete 指令
                        } else if (delete.equals(invocation.getMethodName())) {
                            if (invocation.getArguments().length != 1) {
                                throw new IllegalArgumentException("The redis delete method arguments mismatch, must only one arguments. interface: " + type.getName() + ", method: " + invocation.getMethodName() + ", url: " + url);
                            }
                            // 删除值
                            resource.del(String.valueOf(invocation.getArguments()[0]).getBytes());
                            // 返回结果
                            return new RpcResult();
                        } else {
                            throw new UnsupportedOperationException("Unsupported method " + invocation.getMethodName() + " in redis service.");
                        }
                    } catch (Throwable t) {
                        RpcException re = new RpcException("Failed to invoke redis service method. interface: " + type.getName() + ", method: " + invocation.getMethodName() + ", url: " + url + ", cause: " + t.getMessage(), t);
                        if (t instanceof TimeoutException || t instanceof SocketTimeoutException) {
                            re.setCode(RpcException.TIMEOUT_EXCEPTION);
                        } else if (t instanceof JedisConnectionException || t instanceof IOException) {
                            re.setCode(RpcException.NETWORK_EXCEPTION);
                        } else if (t instanceof JedisDataException) {
                            re.setCode(RpcException.SERIALIZATION_EXCEPTION);
                        }
                        throw re;
                    } finally {
                        // 归还 Redis Resource
                        if (resource != null) {
                            try {
                                jedisPool.returnResource(resource);
                            } catch (Throwable t) {
                                logger.warn("returnResource error: " + t.getMessage(), t);
                            }
                        }
                    }
                }

                @Override
                public void destroy() {
                    // 标记销毁
                    super.destroy();
                    // 销毁 Redis Pool
                    try {
                        jedisPool.destroy();
                    } catch (Throwable e) {
                        logger.warn(e.getMessage(), e);
                    }
                }

            };
        } catch (Throwable t) {
            throw new RpcException("Failed to refer redis service. interface: " + type.getName() + ", url: " + url + ", cause: " + t.getMessage(), t);
        }
    }

}
