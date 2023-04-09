package org.geekbang.time.commonmistakes.exception.stacktrace;

import org.springframework.util.StopWatch;

import java.util.stream.IntStream;

public class CommonMistakesApplication {

    public static void main(String[] args) {
        test2();
    }

    private static void test1() {
        String msg = null;
        for (int i = 0; i < 200000; i++) {
            try {
                msg.toString();
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getStackTrace().length == 0) {
                    System.out.println("StackTrace is empty on " + i + " times!");
                    return;
                }
            }
        }
    }

    private static void test2() {
        try {
            throw new MyPerformanceException("writableStackTrace == true", true);
        } catch (MyPerformanceException e) {
            e.printStackTrace();
        }

        try {
            throw new MyPerformanceException("writableStackTrace == false", false);
        } catch (MyPerformanceException e) {
            e.printStackTrace();
        }

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("writableStackTrace == true");
        IntStream.range(1, 100000).forEach(i -> {
            try {
                throw new MyPerformanceException(null, true);
            } catch (MyPerformanceException e) {

            }
        });
        stopWatch.stop();
        stopWatch.start("writableStackTrace == false");
        IntStream.range(1, 100000).forEach(i -> {
            try {
                throw new MyPerformanceException(null, false);
            } catch (MyPerformanceException e) {

            }
        });
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}

