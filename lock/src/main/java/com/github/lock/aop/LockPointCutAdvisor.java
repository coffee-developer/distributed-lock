package com.github.lock.aop;

import lombok.AllArgsConstructor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

/**
 * @author wkx
 */
@AllArgsConstructor
public class LockPointCutAdvisor extends AbstractPointcutAdvisor {

    private static final  LockPointcut LOCK_POINTCUT = new LockPointcut();

    private LockMethodInterceptor methodInterceptor;

    /**
     * 判断某个方法是否需要执行这个aop,一个方法只执行一遍,猜测spring会缓存判断结果
     */
    @Override
    public Pointcut getPointcut() {
        return LOCK_POINTCUT;
    }

    @Override
    public Advice getAdvice() {
        return methodInterceptor;
    }
}
