package com.github.lock;

import com.github.lock.exception.LockException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁默认实现
 * @author wkx
 */
@AllArgsConstructor
public class DefaultDistributedLockImpl implements DistributedLock {

    private RedisTemplate redisTemplate;

    private static final ThreadLocal<Pair<String, Long>> LOCK_MSG_CACHE = new InheritableThreadLocal<>();

    private static final String DEL_LOCK_SCRIPT = "if redis.call('get',KEYS[1]) == ARGV[1] then redis.call('del',KEYS[1]) end" ;

    private static final RedisScript<Void> REDIS_DEL_LOCK_SCRIPT = new DefaultRedisScript<>(DEL_LOCK_SCRIPT);

    /**
     * 获取分布式锁重试次数
     */
    public static final int RE_TRY_TIME = 5;

    @Override
    public void lock(String key, long time, TimeUnit unit) throws LockException {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();

        boolean flag = false;
        for (int i = 0; i < RE_TRY_TIME; i++) {
            long currentTimeMillis = System.currentTimeMillis();
            flag = valueOperations.setIfAbsent(key, currentTimeMillis, time, unit);
            if (flag) {
                LOCK_MSG_CACHE.set(Pair.of(key, currentTimeMillis));
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) { }
        }

        if (!flag) {
            throw new LockException("获取分布式锁失败");
        }
    }

    @Override
    public void unlock() {
        Pair<String, Long> lockMsg = LOCK_MSG_CACHE.get();
        if (lockMsg == null) {
            return;
        }
        LOCK_MSG_CACHE.remove();
        redisTemplate.execute(REDIS_DEL_LOCK_SCRIPT, List.of(lockMsg.getLeft()), lockMsg.getRight());
    }
}
