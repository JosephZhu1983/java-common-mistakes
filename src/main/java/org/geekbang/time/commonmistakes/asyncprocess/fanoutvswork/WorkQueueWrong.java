package org.geekbang.time.commonmistakes.asyncprocess.fanoutvswork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
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
@RequestMapping("workqueuewrong")
public class WorkQueueWrong {

    private static final String EXCHANGE = "newuserExchange";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public void sendMessage() {
        rabbitTemplate.convertAndSend(EXCHANGE, "", UUID.randomUUID().toString());
    }

    @Bean
    public Queue queue() {
        return new AnonymousQueue();
    }

    @Bean
    public Declarables declarables() {
        DirectExchange exchange = new DirectExchange(EXCHANGE);
        return new Declarables(queue(), exchange,
                BindingBuilder.bind(queue()).to(exchange).with(""));
    }

    @RabbitListener(queues = "#{queue.name}")
    public void memberService(String userName) {
        log.info("memberService: welcome message sent to new user {} from {}", userName, System.getProperty("server.port"));

    }
}
