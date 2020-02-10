package com.github.lock.aop;

import com.github.lock.DistributedLock;
import com.github.lock.el.LockExpressionEvaluator;
import lombok.AllArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.expression.AnnotatedElementKey;

/**
 * 分布式锁主要实现逻辑
 * @author wkx
 */
@AllArgsConstructor
public class LockMethodInterceptor implements MethodInterceptor {

    private DistributedLock distributedLock;

    private LockExpressionEvaluator lockExpressionEvaluator;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        LockAttributeSource attributeSource = LockAttributeSourceHolder.getAttributeSource(invocation.getMethod());
        if (attributeSource == null) {
            return invocation.proceed();
        }

        String key;
        if (StringUtils.isBlank(attributeSource.getKey())) {
            key = attributeSource.getPrefix();
        } else {
            AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(invocation.getMethod(), invocation.getClass());
            String elValue = lockExpressionEvaluator.key(attributeSource.getKey(), annotatedElementKey, invocation.getClass(), invocation.getMethod(), invocation.getThis(), invocation.getArguments());
            key = attributeSource.getPrefix() + elValue;
        }


        distributedLock.lock(key, attributeSource.getExpire(), attributeSource.getUnit());
        try {
            return invocation.proceed();
        } finally {
            distributedLock.unlock();
        }
    }
}
