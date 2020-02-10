package com.github.lock;


import com.github.lock.exception.LockException;

import java.util.concurrent.TimeUnit;

/**
 * 定义分布式锁的行为
 * @author wkx
 */
public interface DistributedLock {
    /**
     * 尝试获取分布式锁
     * @param key 分布式锁的key值
     * @param time 独占锁的时间
     * @param unit 独占锁的时间单位i
     * @throws LockException 当获取不到锁的时候抛出
     */
    void lock(String key, long time, TimeUnit unit) throws LockException;

    /**
     * 关闭锁
     */
    void unlock();
}
