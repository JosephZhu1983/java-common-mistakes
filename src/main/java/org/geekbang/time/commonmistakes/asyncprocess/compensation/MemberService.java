package org.geekbang.time.commonmistakes.asyncprocess.compensation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MemberService {
    private Map<Long, Boolean> welcomeStatus = new ConcurrentHashMap<>();

    @RabbitListener(queues = RabbitConfiguration.QUEUE)
    public void listen(User user) {
        log.info("receive mq user {}", user.getId());
        welcome(user);
    }

    public void welcome(User user) {
        if (welcomeStatus.putIfAbsent(user.getId(), true) == null) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
            log.info("memberService: welcome new user {}", user.getId());
        }
    }
}
