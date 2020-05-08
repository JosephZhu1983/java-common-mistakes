package org.geekbang.time.commonmistakes.asyncprocess.compensation;

import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CompensationJob {
    private static ThreadPoolExecutor compensationThreadPool = new ThreadPoolExecutor(
            10, 10,
            1, TimeUnit.HOURS,
            new ArrayBlockingQueue<>(1000),
            new ThreadFactoryBuilder().setNameFormat("compensation-threadpool-%d").get());
    @Autowired
    private UserService userService;
    @Autowired
    private MemberService memberService;
    private long offset = 0;

    @Scheduled(initialDelay = 10_000, fixedRate = 5_000)
    public void compensationJob() {
        log.info("开始从用户ID {} 补偿", offset);
        userService.getUsersAfterIdWithLimit(offset, 5).forEach(user -> {
            compensationThreadPool.execute(() -> memberService.welcome(user));
            offset = user.getId();
        });
    }
}
