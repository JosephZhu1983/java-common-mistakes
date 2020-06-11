package org.geekbang.time.commonmistakes.asyncprocess.rabbitmqdlx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
@Slf4j
public class RabbitConfiguration implements RabbitListenerConfigurer {
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public MessagePropertiesConverter messagePropertiesConverter() {
        return new DefaultMessagePropertiesConverter();
    }

    @Bean
    public Declarables declarablesForWorker() {
        Queue queue = new Queue(Consts.QUEUE);
        DirectExchange directExchange = new DirectExchange(Consts.EXCHANGE);
        return new Declarables(queue, directExchange,
                BindingBuilder.bind(queue).to(directExchange).with(Consts.ROUTING_KEY));
    }

    @Bean
    public Declarables declarablesForBuffer() {
        Queue queue = QueueBuilder.durable(Consts.BUFFER_QUEUE)
                .withArgument("x-dead-letter-exchange", Consts.EXCHANGE)
                .withArgument("x-dead-letter-routing-key", Consts.ROUTING_KEY)
                .withArgument("x-message-ttl", Consts.RETRY_INTERNAL)
                .build();
        DirectExchange directExchange = new DirectExchange(Consts.BUFFER_EXCHANGE);
        return new Declarables(queue, directExchange,
                BindingBuilder.bind(queue).to(directExchange).with(Consts.BUFFER_ROUTING_KEY));
    }

    @Bean
    public Declarables declarablesForDead() {
        Queue queue = new Queue(Consts.DEAD_QUEUE);
        DirectExchange directExchange = new DirectExchange(Consts.DEAD_EXCHANGE);
        return new Declarables(queue, directExchange,
                BindingBuilder.bind(queue).to(directExchange).with(Consts.DEAD_ROUTING_KEY));
    }
}
