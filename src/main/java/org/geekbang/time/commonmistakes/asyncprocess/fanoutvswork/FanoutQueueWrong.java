package org.geekbang.time.commonmistakes.asyncprocess.fanoutvswork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Slf4j
//@Configuration
//@RestController
@RequestMapping("fanoutwrong")
public class FanoutQueueWrong {
    private static final String QUEUE = "newuser";
    private static final String EXCHANGE = "newuser";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public void sendMessage() {
        rabbitTemplate.convertAndSend(EXCHANGE, "", UUID.randomUUID().toString());
    }

    @Bean
    public Declarables declarables() {
        Queue queue = new Queue(QUEUE);
        FanoutExchange exchange = new FanoutExchange(EXCHANGE);
        return new Declarables(queue, exchange,
                BindingBuilder.bind(queue).to(exchange));
    }

    @RabbitListener(queues = QUEUE)
    public void memberService1(String userName) {
        log.info("memberService1: welcome message sent to new user {}", userName);

    }

    @RabbitListener(queues = QUEUE)
    public void memberService2(String userName) {
        log.info("memberService2: welcome message sent to new user {}", userName);

    }

    @RabbitListener(queues = QUEUE)
    public void promotionService1(String userName) {
        log.info("promotionService1: gift sent to new user {}", userName);
    }

    @RabbitListener(queues = QUEUE)
    public void promotionService2(String userName) {
        log.info("promotionService2: gift sent to new user {}", userName);
    }
}
