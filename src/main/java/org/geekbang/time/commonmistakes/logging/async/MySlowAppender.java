package org.geekbang.time.commonmistakes.logging.async;

import ch.qos.logback.core.ConsoleAppender;

import java.util.concurrent.TimeUnit;

public class MySlowAppender extends ConsoleAppender {
    @Override
    protected void subAppend(Object event) {
        try {
            // 模拟慢日志
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.subAppend(event);
    }
}
