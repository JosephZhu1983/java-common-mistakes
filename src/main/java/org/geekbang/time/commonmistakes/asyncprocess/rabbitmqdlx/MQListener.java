package org.geekbang.time.commonmistakes.asyncprocess.rabbitmqdlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MQListener {
    @Autowired
    private MessagePropertiesConverter messagePropertiesConverter;

    @RabbitListener(queues = Consts.QUEUE)
    public void handler(@Payload Message message, Channel channel) throws IOException {
        String m = new String(message.getBody());
        try {
            log.info("Handler 收到消息：{}", m);
            throw new RuntimeException("处理消息失败");
        } catch (Exception e) {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            Long retryCount = getRetryCount(headers);
            if (retryCount < Consts.RETRY_COUNT) {
                log.info("Handler 消费消息：{} 异常，准备重试第{}次", m, ++retryCount);

                AMQP.BasicProperties rabbitMQProperties =
                        messagePropertiesConverter.fromMessageProperties(message.getMessageProperties(), "UTF-8");
                rabbitMQProperties.builder().headers(headers);
                channel.basicPublish(Consts.BUFFER_EXCHANGE, Consts.BUFFER_ROUTING_KEY, rabbitMQProperties, message.getBody());
            } else {
                log.info("Handler 消费消息：{} 异常，已重试 {} 次，发送到死信队列处理！", m, Consts.RETRY_COUNT);
                channel.basicPublish(Consts.DEAD_EXCHANGE, Consts.DEAD_ROUTING_KEY, null, message.getBody());
            }
        }
    }

    private long getRetryCount(Map<String, Object> headers) {
        long retryCount = 0;
        if (null != headers) {
            if (headers.containsKey("x-death")) {
                List<Map<String, Object>> deathList = (List<Map<String, Object>>) headers.get("x-death");
                if (!deathList.isEmpty()) {
                    Map<String, Object> deathEntry = deathList.get(0);
                    retryCount = (Long) deathEntry.get("count");
                }
            }
        }
        return retryCount;
    }

    @RabbitListener(queues = Consts.DEAD_QUEUE)
    public void deadHandler(@Payload Message message) {
        log.error("DeadHandler 收到死信消息： {}", new String(message.getBody()));
    }
}
