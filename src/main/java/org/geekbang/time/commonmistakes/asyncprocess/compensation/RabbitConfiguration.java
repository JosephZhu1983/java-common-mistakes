package org.geekbang.time.commonmistakes.asyncprocess.compensation;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    public static final String QUEUE = "newuserQueueCompensation";
    public static final String EXCHANGE = "newuserExchangeCompensation";
    public static final String ROUTING_KEY = "newuserRoutingCompensation";

    //队列
    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    //交换器
    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(EXCHANGE).durable(true).build();
    }

    //绑定
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY).noargs();
    }
}
