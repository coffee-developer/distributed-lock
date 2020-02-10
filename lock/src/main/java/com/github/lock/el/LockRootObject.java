package com.github.lock.el;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 用于解析el表达式
 * 参考spring  cache的{@link org.springframework.cache.interceptor.CacheExpressionRootObject}
 * @author wkx
 */
@Data
public class LockRootObject {
    private final Method method;

    private final Object[] args;

    private final Object target;

    private final Class<?> targetClass;
}
