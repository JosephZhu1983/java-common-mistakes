package org.geekbang.time.commonmistakes.java8.completablefuture;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

@Slf4j
public class CompletableFutureTest {

    private static Long orderId = 123L;
    ExecutorService threadPool = Executors.newFixedThreadPool(10);

    @Test
    public void testCompletableFuture() throws ExecutionException, InterruptedException {
        long begin = System.currentTimeMillis();

        Order result = CompletableFuture.supplyAsync(() -> Services.getOrder(orderId))
                .whenCompleteAsync((order, __) -> CompletableFuture.allOf(
                        CompletableFuture.runAsync(() -> order.setCouponPrice(Services.getCouponPrice(order.getCouponId()))),
                        CompletableFuture.runAsync(() -> order.setUser(Services.getUser(order.getUserId()))),
                        CompletableFuture.runAsync(() -> order.setMerchant(Services.getMerchant(order.getMerchantId())))
                ).join()).whenCompleteAsync((order, __) ->
                        CompletableFuture.allOf(
                                CompletableFuture.supplyAsync(() -> Services.calcOrderPrice(order.getItemPrice(), order.getUser().getVip())).thenAccept(order::setOrderPrice),
                                CompletableFuture.supplyAsync(() -> Services.getWalkDistance("from", "to"))
                                        .exceptionally(ex -> {
                                            ex.printStackTrace();
                                            return Services.getDirectDistance("from", "to");
                                        })
                                        .thenAcceptBoth(CompletableFuture.anyOf(
                                                CompletableFuture.supplyAsync(Services::getWeatherA),
                                                CompletableFuture.supplyAsync(Services::getWeatherB)),
                                                (distance, weather) -> order.setDeliverPrice(Services.calcDeliverPrice(order.getMerchant().getAverageWaitMinutes(), distance, (String) weather))))
                                .whenComplete((aVoid, ex) -> order.setTotalPrice(order.getOrderPrice().add(order.getDeliverPrice()).subtract(order.getCouponPrice())))
                                .join()).get();

        log.info("CompletableFuture order:{} took:{} ms", result, System.currentTimeMillis() - begin);
    }

    @Test
    public void testNormal() {
        long begin = System.currentTimeMillis();

        Future<String> weather1 = threadPool.submit(Services::getWeatherA);
        Future<String> weather2 = threadPool.submit(Services::getWeatherB);

        Order order = Services.getOrder(orderId);
        order.setUser(Services.getUser(order.getUserId()));
        order.setMerchant(Services.getMerchant(order.getMerchantId()));
        order.setCouponPrice(Services.getCouponPrice(order.getCouponId()));
        Integer distance = null;
        try {
            distance = Services.getWalkDistance(order.getFrom(), order.getTo());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (distance == null)
            distance = Services.getDirectDistance(order.getFrom(), order.getTo());

        String weather = null;
        if (weather1.isDone()) {
            try {
                weather = weather1.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                weather = weather2.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        order.setDeliverPrice(Services.calcDeliverPrice(order.getMerchant().getAverageWaitMinutes(), distance, weather));
        order.setOrderPrice(Services.calcOrderPrice(order.getItemPrice(), order.getUser().getVip()));
        order.setTotalPrice(order.getOrderPrice().add(order.getDeliverPrice()).subtract(order.getCouponPrice()));
        log.info("Normal order:{} took:{} ms", order, System.currentTimeMillis() - begin);
    }
}
