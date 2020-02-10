package com.github.lock.aop;

import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存{@link Lock}上的信息
 * @author wkx
 */
@UtilityClass
public class LockAttributeSourceHolder {
    private ConcurrentHashMap<Method, LockAttributeSource> lockAttributeSourceMap = new ConcurrentHashMap<>();

    public LockAttributeSource getAttributeSource(Method method) {
        return lockAttributeSourceMap.computeIfAbsent(method, m -> {
            if ((method.getDeclaringClass() == Object.class)) {
                return null;
            }

            Lock lock = AnnotationUtils.getAnnotation(m, Lock.class);

            if (lock == null) {
                return null;
            }

            LockAttributeSource lockAttributeSource = new LockAttributeSource();
            lockAttributeSource.setPrefix(lock.prefix());
            lockAttributeSource.setKey(lock.key());
            lockAttributeSource.setExpire(lock.expire());
            lockAttributeSource.setUnit(lock.unit());

            return lockAttributeSource;
        });
    }

    public boolean ifHashMark(Method method) {
        LockAttributeSource lockAttributeSource = getAttributeSource(method);
        return lockAttributeSource != null;
    }
}
