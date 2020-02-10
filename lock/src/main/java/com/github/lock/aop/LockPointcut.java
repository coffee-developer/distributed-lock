package com.github.lock.aop;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;


/**
 * @author wkx
 */
public class LockPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return LockAttributeSourceHolder.ifHashMark(method);
    }
}
