package com.github.lock.exception;

/**
 * @author wkx
 */
public class LockException extends Exception {
    private static final long serialVersionUID = 1L;

    public LockException(String msg) {
        super(msg);
    }
    public LockException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
