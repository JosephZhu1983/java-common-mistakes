package org.geekbang.time.commonmistakes.productionready.metrics;

import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

//配送服务消息处理程序
@RestController
@Slf4j
@RequestMapping("deliver")
public class DeliverOrderHandler {

    //配送服务运行状态
    private volatile boolean deliverStatus = true;
    private AtomicLong deliverCounter = new AtomicLong();

    //通过一个外部接口来改变配送状态模拟配送服务停工
    @PostMapping("status")
    public void status(@RequestParam("status") boolean status) {
        deliverStatus = status;
    }

    @PostConstruct
    public void init() {
        //同样注册一个gauge指标deliverOrder.totalSuccess，代表总的配送单量，只需注册一次即可
        Metrics.gauge("deliverOrder.totalSuccess", deliverCounter);
    }

    //监听MQ消息
    @RabbitListener(queues = Consts.QUEUE, concurrency = "5")
    public void deliverOrder(Order order) {
        Instant begin = Instant.now();
        //对deliverOrder.received进行递增，代表收到一次订单消息，counter类型
        Metrics.counter("deliverOrder.received").increment();
        try {
            if (!deliverStatus)
                throw new RuntimeException("deliver outofservice");
            TimeUnit.MILLISECONDS.sleep(500);
            deliverCounter.incrementAndGet();
            //配送成功指标deliverOrder.success，timer类型
            Metrics.timer("deliverOrder.success").record(Duration.between(begin, Instant.now()));
        } catch (Exception ex) {
            log.error("deliver Order {} failed", order, ex);
            //配送失败指标deliverOrder.failed，同样附加了失败原因作为tags，timer类型
            Metrics.timer("deliverOrder.failed", "reason", ex.getMessage()).record(Duration.between(begin, Instant.now()));
        }
    }
}
