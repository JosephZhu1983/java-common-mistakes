package org.geekbang.time.commonmistakes.java8;

import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ForEachOrderedTest {
    private static void consume(int i) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(i);
    }

    private static boolean filter(int i) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i % 2 == 0;
    }

    @Test
    public void test() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(10));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("stream");
        stream();
        stopWatch.stop();
        stopWatch.start("parallelStream");
        parallelStream();
        stopWatch.stop();
        stopWatch.start("parallelStreamForEachOrdered");
        parallelStreamForEachOrdered();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private void stream() {
        IntStream.rangeClosed(1, 10)
                .filter(ForEachOrderedTest::filter)
                .forEach(ForEachOrderedTest::consume);
    }

    private void parallelStream() {
        IntStream.rangeClosed(1, 10).parallel()
                .filter(ForEachOrderedTest::filter)
                .forEach(ForEachOrderedTest::consume);
    }

    private void parallelStreamForEachOrdered() {
        IntStream.rangeClosed(1, 10).parallel()
                .filter(ForEachOrderedTest::filter)
                .forEachOrdered(ForEachOrderedTest::consume);
    }
}
