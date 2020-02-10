package com.github.lock.example.service.impl;

import com.github.lock.DistributedLock;
import com.github.lock.aop.Lock;
import com.github.lock.example.service.ExampleService;
import com.github.lock.exception.LockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author wkx
 */
@Slf4j
@Service
public class ExampleServiceImpl implements ExampleService {
    @Autowired
    private DistributedLock distributedLock;

    @Override
    @Lock(prefix = "com.github.lock.test", key = "#i", expire = 1, unit = TimeUnit.MINUTES)
    public void test1(String i) {
        try {
            Thread.sleep(TimeUnit.MINUTES.toMillis(1));
        } catch (InterruptedException e) { }
        log.info("test   {}" , System.currentTimeMillis());
    }

    @Override
    public void test2(String i) {
        try {
            distributedLock.lock("com.github.test" + i, 1, TimeUnit.MINUTES);
        } catch (LockException e) {
            throw new RuntimeException(e);
        }
        try {
            try {
                Thread.sleep(TimeUnit.MINUTES.toMillis(1));
            } catch (InterruptedException e) { }
            log.info("test  {}", System.currentTimeMillis());
        } finally {
            distributedLock.unlock();
        }
    }


}
