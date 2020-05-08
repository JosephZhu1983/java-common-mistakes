package org.geekbang.time.commonmistakes.asyncprocess.deadletter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MQListener {
    @RabbitListener(queues = Consts.QUEUE)
    public void handler(String data) {
        //http://localhost:15672/#/
        log.info("got message {}", data);
        throw new NullPointerException("error");
        //throw new AmqpRejectAndDontRequeueException("error");
    }

    @RabbitListener(queues = Consts.DEAD_QUEUE)
    public void deadHandler(String data) {
        log.error("got dead message {}", data);
    }
}
