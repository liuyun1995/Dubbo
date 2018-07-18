package com.alibaba.dubbo.config.spring.context.annotation;

import com.alibaba.dubbo.config.AbstractConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DubboConfigBindingRegistrar.class)
public @interface EnableDubboConfigBinding {

    String prefix();

    Class<? extends AbstractConfig> type();

    boolean multiple() default false;

}
