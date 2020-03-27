package org.geekbang.time.commonmistakes.exception.threadpoolandexception;

import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@Slf4j
@RequestMapping("threadpoolandexception")
public class ThreadPoolAndExceptionController {

    static {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> log.error("Thread {} got exception", thread, throwable));
    }

    @GetMapping("execute")
    public void execute() throws InterruptedException {

        String prefix = "test";
        ExecutorService threadPool = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder()
                .setNameFormat(prefix + "%d")
                .setUncaughtExceptionHandler((thread, throwable) -> log.error("ThreadPool {} got exception", thread, throwable))
                .get());
        IntStream.rangeClosed(1, 10).forEach(i -> threadPool.execute(() -> {
            if (i == 5) throw new RuntimeException("error");
            log.info("I'm done : {}", i);
        }));

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    @GetMapping("submit")
    public void submit() throws InterruptedException {

        String prefix = "test";
        ExecutorService threadPool = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder().setNameFormat(prefix + "%d").get());
        IntStream.rangeClosed(1, 10).forEach(i -> threadPool.submit(() -> {
            if (i == 5) throw new RuntimeException("error");
            log.info("I'm done : {}", i);
        }));

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    @GetMapping("submitright")
    public void submitRight() throws InterruptedException {

        String prefix = "test";
        ExecutorService threadPool = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder().setNameFormat(prefix + "%d").get());

        List<Future> tasks = IntStream.rangeClosed(1, 10).mapToObj(i -> threadPool.submit(() -> {
            if (i == 5) throw new RuntimeException("error");
            log.info("I'm done : {}", i);
        })).collect(Collectors.toList());

        tasks.forEach(task -> {
            try {
                task.get();
            } catch (Exception e) {
                log.error("Got exception", e);
            }
        });
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }
}
