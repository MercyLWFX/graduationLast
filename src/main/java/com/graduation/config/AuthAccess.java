package com.graduation.config;


import java.lang.annotation.*;

/**
 * 自定义注解
 * 该注解表名后表示这个接口不需要过滤器进行拦截
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAccess {
//    boolean proxyBeanMethods() default true;
}
