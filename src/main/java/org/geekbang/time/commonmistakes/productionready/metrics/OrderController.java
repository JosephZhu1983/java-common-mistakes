package org.geekbang.time.commonmistakes.productionready.metrics;

import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

//下单操作，以及商户服务的接口
@Slf4j
@RestController
@RequestMapping("order")
public class OrderController {
    //总订单创建数量
    private AtomicLong createOrderCounter = new AtomicLong();
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        //注册createOrder.received指标，gauge指标只需要像这样初始化一次，直接关联到AtomicLong引用即可
        Metrics.gauge("createOrder.totalSuccess", createOrderCounter);
    }

    //下单接口，提供用户ID和商户ID作为入参
    @GetMapping("createOrder")
    public void createOrder(@RequestParam("userId") long userId, @RequestParam("merchantId") long merchantId) {
        //记录一次createOrder.received指标，表示收到下单请求
        Metrics.counter("createOrder.received").increment();
        Instant begin = Instant.now();
        try {
            TimeUnit.MILLISECONDS.sleep(200);
            //模拟无效用户的情况，ID<10的为无效用户
            if (userId < 10)
                throw new RuntimeException("invalid user");
            //查询商户服务
            Boolean merchantStatus = restTemplate.getForObject("http://localhost:45678/order/getMerchantStatus?merchantId=" + merchantId, Boolean.class);
            if (merchantStatus == null || !merchantStatus)
                throw new RuntimeException("closed merchant");
            Order order = new Order();
            order.setId(createOrderCounter.incrementAndGet()); //gauge指标可以得到自动更新
            order.setUserId(userId);
            order.setMerchantId(merchantId);
            //发送MQ消息
            rabbitTemplate.convertAndSend(Consts.EXCHANGE, Consts.ROUTING_KEY, order);
            //记录一次createOrder.success指标，表示下单成功，同时提供耗时
            Metrics.timer("createOrder.success").record(Duration.between(begin, Instant.now()));
        } catch (Exception ex) {
            log.error("creareOrder userId {} failed", userId, ex);
            //记录一次createOrder.failed指标，表示下单失败，同时提供耗时，并且以tag记录失败原因
            Metrics.timer("createOrder.failed", "reason", ex.getMessage()).record(Duration.between(begin, Instant.now()));
        }
    }

    //商户查询接口
    @GetMapping("getMerchantStatus")
    public boolean getMerchantStatus(@RequestParam("merchantId") long merchantId) throws InterruptedException {
        //只有商户ID为2的商户才是营业的
        TimeUnit.MILLISECONDS.sleep(200);
        return merchantId == 2;
    }
}
