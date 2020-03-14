package org.geekbang.time.commonmistakes.java8.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Services {

    public static Order getOrder(Long id) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Order(id, "from", "to", 1L, 2L, 3L, new BigDecimal("20"));
    }

    public static User getUser(Long id) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return User.builder().id(id).vip(true).build();
    }

    public static Merchant getMerchant(Long id) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Merchant.builder().id(id).averageWaitMinutes(15).build();
    }

    public static BigDecimal getCouponPrice(Long id) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new BigDecimal("1");
    }

    public static BigDecimal calcOrderPrice(BigDecimal itemPrice, boolean isVip) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isVip)
            return itemPrice.multiply(new BigDecimal("0.9"));
        else
            return itemPrice;
    }

    public static BigDecimal calcDeliverPrice(int averageWaitMinutes, int distance, String weather) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BigDecimal price = new BigDecimal("5")
                .add(BigDecimal.valueOf(distance * 1.5 / 1000));

        if (averageWaitMinutes > 10)
            price = price.add(BigDecimal.valueOf((averageWaitMinutes - 10) * 0.1));

        Map<String, BigDecimal> weatherPrice = new HashMap<>();
        weatherPrice.put("Cloudy", new BigDecimal("1"));
        weatherPrice.put("Sunny", new BigDecimal("1.5"));
        price = price.add(weatherPrice.get(weather));
        return price;
    }

    public static Integer getWalkDistance(String from, String to) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Walk distance service unavailable!");
    }

    public static Integer getDirectDistance(String from, String to) {
        return 1000;
    }

    public static String getWeatherA() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Cloudy";
    }

    public static String getWeatherB() {
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Sunny";
    }
}
