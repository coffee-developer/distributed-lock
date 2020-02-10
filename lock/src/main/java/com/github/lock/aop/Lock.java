package com.github.lock.aop;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解
 * 默认过期时间 1秒
 * @author wkx
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {
    /**
     * 分布式锁key值前缀
     */
    String prefix() default "";

    /**
     * 分布式锁key后缀
     * 支持spring el表达式
     */
    String key();

    /**
     * 过期时间单位
     */
    TimeUnit unit();

    /**
     * 过期时间长度
     */
    long expire();
}
