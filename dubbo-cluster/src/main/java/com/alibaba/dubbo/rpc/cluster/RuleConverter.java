package com.alibaba.dubbo.rpc.cluster;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.SPI;

import java.util.List;

@SPI
@Deprecated // add by 芋艿，实际未使用
public interface RuleConverter {

    List<URL> convert(URL subscribeUrl, Object source);

}