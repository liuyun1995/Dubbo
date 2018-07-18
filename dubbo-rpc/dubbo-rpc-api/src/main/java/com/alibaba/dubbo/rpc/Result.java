package com.alibaba.dubbo.rpc;

import java.util.Map;

//调用结果
public interface Result {

    //获取调用结果值
    Object getValue();

    //获取异常
    Throwable getException();

    //是否抛出异常
    boolean hasException();

    /**
     * Recreate.
     * <p>
     * <code>
     * if (hasException()) {
     * throw getException();
     * } else {
     * return getValue();
     * }
     * </code>
     *
     * @return result.
     * @throws if has exception throw it.
     */
    Object recreate() throws Throwable;

    /**
     * @see com.alibaba.dubbo.rpc.Result#getValue()
     * @deprecated Replace to getValue()
     */
    @Deprecated
    Object getResult();


    /**
     * get attachments.
     *
     * @return attachments.
     */
    Map<String, String> getAttachments();

    /**
     * get attachment by key.
     *
     * @return attachment value.
     */
    String getAttachment(String key);

    /**
     * get attachment by key with default value.
     *
     * @return attachment value.
     */
    String getAttachment(String key, String defaultValue);

}