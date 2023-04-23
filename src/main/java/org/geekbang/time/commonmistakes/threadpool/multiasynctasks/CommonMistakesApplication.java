package org.geekbang.time.commonmistakes.threadpool.multiasynctasks;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class CommonMistakesApplication {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test3();
    }

    private static void test1() {
        long begin = System.currentTimeMillis();
        List<Future<Integer>> futures = IntStream.rangeClosed(1, 4)
                .mapToObj(i -> threadPool.submit(getAsyncTask(i)))
                .collect(Collectors.toList());
        List<Integer> result = futures.stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }).collect(Collectors.toList());
        log.info("result {} took {} ms", result, System.currentTimeMillis() - begin);
    }

    private static Callable<Integer> getAsyncTask(int i) {
        return () -> {
            TimeUnit.SECONDS.sleep(i);
            return i;
        };
    }

    private static void test2() {
        long begin = System.currentTimeMillis();
        int count = 4;
        List<Integer> result = new ArrayList<>(count);
        CountDownLatch countDownLatch = new CountDownLatch(count);
        IntStream.rangeClosed(1, count).forEach(i -> threadPool.execute(executeAsyncTask(i, countDownLatch, result)));
        try {
            countDownLatch.await(3500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("result {} took {} ms", result, System.currentTimeMillis() - begin);
    }
    private static Runnable executeAsyncTask(int i, CountDownLatch countDownLatch, List<Integer> result) {
        return () -> {
            try {
                TimeUnit.SECONDS.sleep(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (result) {
                result.add(i);
            }
            countDownLatch.countDown();
        };
    }

    private static void test3() throws ExecutionException, InterruptedException {
        long begin = System.currentTimeMillis();
        List<Integer> result = new ArrayList<>();
        List<CompletableFuture<Integer>> futures = IntStream.rangeClosed(1, 4)
                .mapToObj(i -> CompletableFuture.
                        supplyAsync(getAsyncTaskSupplier(i))
                        .whenComplete((r, t) -> result.add(r)))
                .collect(Collectors.toList());

        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                    .get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        log.info("result {} took {} ms", result, System.currentTimeMillis() - begin);
    }

    private static Supplier<Integer> getAsyncTaskSupplier(int i) {
        return () -> {
            try {
                TimeUnit.SECONDS.sleep(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return i;
        };
    }

}

