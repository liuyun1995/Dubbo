package com.alibaba.dubbo.rpc;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//静态上下文
public class StaticContext extends ConcurrentHashMap<Object, Object> {

    private static final long serialVersionUID = 1L;

    private static final String SYSTEMNAME = "system";
    //缓存
    private static final ConcurrentMap<String, StaticContext> context_map = new ConcurrentHashMap<String, StaticContext>();
    //名字
    private String name;

    private StaticContext(String name) {
        super();
        this.name = name;
    }

    public static StaticContext getSystemContext() {
        return getContext(SYSTEMNAME);
    }

    public static StaticContext getContext(String name) {
        StaticContext appContext = context_map.get(name);
        if (appContext == null) {
            appContext = context_map.putIfAbsent(name, new StaticContext(name));
            if (appContext == null) {
                appContext = context_map.get(name);
            }
        }
        return appContext;
    }

    public static StaticContext remove(String name) {
        return context_map.remove(name);
    }

    public static String getKey(URL url, String methodName, String suffix) {
        return getKey(url.getServiceKey(), methodName, suffix);
    }

    /**
     * 获得服务名对应的 {@link #context_map} 键
     *
     * @param paras 参数集合
     * @param methodName 方法名
     * @param suffix 后缀
     * @return 键
     */
    public static String getKey(Map<String, String> paras, String methodName, String suffix) {
        return getKey(StringUtils.getServiceKey(paras), methodName, suffix);
    }

    /**
     * 获得服务名对应的 {@link #context_map} 键
     *
     * 格式，${serviceKey}.${methodName}.${suffix}
     * 其中，${suffix} 一般是，oninvoke.method、onreturn.method、onthrow.method
     *
     * @param servicekey 服务键
     * @param methodName 方法名
     * @param suffix 后缀
     * @return 键
     */
    private static String getKey(String servicekey, String methodName, String suffix) {
        StringBuffer sb = new StringBuffer().append(servicekey).append(".").append(methodName).append(".").append(suffix);
        return sb.toString();
    }

    public String getName() {
        return name;
    }
}