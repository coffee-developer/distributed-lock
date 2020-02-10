package com.github.lock.config;


import com.github.lock.DefaultDistributedLockImpl;
import com.github.lock.DistributedLock;
import com.github.lock.aop.LockMethodInterceptor;
import com.github.lock.aop.LockPointCutAdvisor;
import com.github.lock.el.LockExpressionEvaluator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author wkx
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class LockAutoConfig {


    @Bean
    public DistributedLock distributedLock(RedisTemplate redisTemplate) {
        return new DefaultDistributedLockImpl(redisTemplate);
    }

    @Bean
    public LockMethodInterceptor lockMethodInterceptor(DistributedLock distributedLock) {
        return new LockMethodInterceptor(distributedLock, lockExpressionEvaluator());
    }

    @Bean
    public LockPointCutAdvisor lockPointCutAdvisor(LockMethodInterceptor lockMethodInterceptor) {
        return new LockPointCutAdvisor(lockMethodInterceptor);
    }

    @Bean
    public LockExpressionEvaluator lockExpressionEvaluator() {
        return new LockExpressionEvaluator();
    }
}
