package com.alibaba.dubbo.config.spring.context.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(DubboConfigConfigurationSelector.class)
public @interface EnableDubboConfig {

    boolean multiple() default false;

}
