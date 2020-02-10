package com.github.lock.aop;

import lombok.Data;

import java.util.concurrent.TimeUnit;


/**
 * @author wkx
 */
@Data
public class LockAttributeSource {
    private String prefix;
    private String key;
    private long expire;
    private TimeUnit unit;
}
