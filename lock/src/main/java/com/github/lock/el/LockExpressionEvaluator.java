package com.github.lock.el;

import com.github.lock.aop.Lock;
import com.github.lock.aop.LockAttributeSource;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析{@link LockAttributeSource} 上的el表达式
 *  * 参考 spring cache的{@link org.springframework.cache.interceptor.CacheOperationExpressionEvaluator}
 * {@link Lock}
 * @author wkx
 */
public class LockExpressionEvaluator extends CachedExpressionEvaluator {
    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);
    private static final ParameterNameDiscoverer DEFAULT_PARAM_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    public String key(String keyExpression, AnnotatedElementKey methodKey, Class<?> targetClass, Method method, Object target, Object[] args) {
        Expression expression = getExpression(this.keyCache, methodKey, keyExpression);

        LockRootObject lockRootObject = new LockRootObject(method, args, target, targetClass);
        MethodBasedEvaluationContext evaluationContext = new MethodBasedEvaluationContext(lockRootObject, method, args, DEFAULT_PARAM_NAME_DISCOVERER);
        return expression.getValue(evaluationContext, String.class);
    }
}
