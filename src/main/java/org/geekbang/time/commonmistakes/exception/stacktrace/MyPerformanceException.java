package org.geekbang.time.commonmistakes.exception.stacktrace;

public class MyPerformanceException extends RuntimeException {

    public MyPerformanceException(String message, boolean performance) {
        super(message, null, false, performance);
    }
}
