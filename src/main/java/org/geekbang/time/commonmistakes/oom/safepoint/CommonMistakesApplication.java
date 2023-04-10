package org.geekbang.time.commonmistakes.oom.safepoint;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CommonMistakesApplication {

    public static void main(String[] args) throws InterruptedException {
        long current = System.currentTimeMillis();
        AtomicInteger num = new AtomicInteger(0);
        Thread t = new Thread(() -> {
            for (int i = 0; i < 1000000000; i++) {
                num.incrementAndGet();
            }
        });
        t.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("took:" + (System.currentTimeMillis() - current) + "ms, num = " + num);
    }
}

