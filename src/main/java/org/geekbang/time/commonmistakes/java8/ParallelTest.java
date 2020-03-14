package org.geekbang.time.commonmistakes.java8;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
public class ParallelTest {

    @Test
    public void parallel() {
        IntStream.rangeClosed(1, 100).parallel().forEach(i -> {
            System.out.println(LocalDateTime.now() + " : " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        });
    }

    @Test
    public void allMethods() throws InterruptedException, ExecutionException {
        int taskCount = 10000;
        int threadCount = 20;
        StopWatch stopWatch = new StopWatch();

        stopWatch.start("thread");
        Assert.assertEquals(taskCount, thread(taskCount, threadCount));
        stopWatch.stop();

        stopWatch.start("threadpool");
        Assert.assertEquals(taskCount, threadpool(taskCount, threadCount));
        stopWatch.stop();

        //试试把这段放到forkjoin下面？
        stopWatch.start("stream");
        Assert.assertEquals(taskCount, stream(taskCount, threadCount));
        stopWatch.stop();

        stopWatch.start("forkjoin");
        Assert.assertEquals(taskCount, forkjoin(taskCount, threadCount));
        stopWatch.stop();

        stopWatch.start("completableFuture");
        Assert.assertEquals(taskCount, completableFuture(taskCount, threadCount));
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());
    }

    private void increment(AtomicInteger atomicInteger) {
        atomicInteger.incrementAndGet();
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int thread(int taskCount, int threadCount) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        IntStream.rangeClosed(1, threadCount).mapToObj(i -> new Thread(() -> {
            IntStream.rangeClosed(1, taskCount / threadCount).forEach(j -> increment(atomicInteger));
            countDownLatch.countDown();
        })).forEach(Thread::start);
        countDownLatch.await();
        return atomicInteger.get();
    }

    private int threadpool(int taskCount, int threadCount) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        IntStream.rangeClosed(1, taskCount).forEach(i -> executorService.execute(() -> increment(atomicInteger)));
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        return atomicInteger.get();
    }

    private int forkjoin(int taskCount, int threadCount) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, taskCount).parallel().forEach(i -> increment(atomicInteger)));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return atomicInteger.get();
    }

    private int stream(int taskCount, int threadCount) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(threadCount));
        AtomicInteger atomicInteger = new AtomicInteger();
        IntStream.rangeClosed(1, taskCount).parallel().forEach(i -> increment(atomicInteger));
        return atomicInteger.get();
    }

    private int completableFuture(int taskCount, int threadCount) throws InterruptedException, ExecutionException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
        CompletableFuture.runAsync(() -> IntStream.rangeClosed(1, taskCount).parallel().forEach(i -> increment(atomicInteger)), forkJoinPool).get();
        return atomicInteger.get();
    }
}
