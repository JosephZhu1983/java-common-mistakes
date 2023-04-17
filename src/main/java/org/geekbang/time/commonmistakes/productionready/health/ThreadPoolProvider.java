package org.geekbang.time.commonmistakes.productionready.health;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolProvider {
    private static ThreadPoolExecutor demoThreadPool = new ThreadPoolExecutor(
            1, 1,
            2, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").build());

    private static ThreadPoolExecutor ioThreadPool = new ThreadPoolExecutor(
            10, 50,
            2, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100),
            new ThreadFactoryBuilder().setNameFormat("io-threadpool-%d").build());

    public static ThreadPoolExecutor getDemoThreadPool() {
        return demoThreadPool;
    }

    public static ThreadPoolExecutor getIOThreadPool() {
        return ioThreadPool;
    }
}
