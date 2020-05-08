package org.geekbang.time.commonmistakes.asyncprocess.compensation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@RestController
@Slf4j
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("register")
    public void register() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            User user = userService.register();
            if (ThreadLocalRandom.current().nextInt(10) % 2 == 0) {
                rabbitTemplate.convertAndSend(RabbitConfiguration.EXCHANGE, RabbitConfiguration.ROUTING_KEY, user);
                log.info("sent mq user {}", user.getId());
            }
        });
    }
}
