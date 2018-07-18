package com.alibaba.dubbo.config.spring.context.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class DubboConfigConfigurationSelector implements ImportSelector, Ordered {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableDubboConfig.class.getName()));

        boolean multiple = attributes.getBoolean("multiple");

        if (multiple) {
            return of(DubboConfigConfiguration.Multiple.class.getName());
        } else {
            return of(DubboConfigConfiguration.Single.class.getName());
        }
    }

    private static <T> T[] of(T... values) {
        return values;
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }


}
