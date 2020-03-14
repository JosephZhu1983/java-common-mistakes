package org.geekbang.time.commonmistakes.connectionpool.jedis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("jedismisreuse")
@Slf4j
public class JedisMisreuseController {

    private static JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);

    @PostConstruct
    public void init() {
        try (Jedis jedis = new Jedis("127.0.0.1", 6379)) {
            Assert.isTrue("OK".equals(jedis.set("a", "1")), "set a = 1 return OK");
            Assert.isTrue("OK".equals(jedis.set("b", "2")), "set b = 2 return OK");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            jedisPool.close();
        }));
    }

    @GetMapping("/wrong")
    public void wrong() throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                String result = jedis.get("a");
                if (!"1".equals(result)) {
                    log.warn("Expect a to be 1 but found {}", result);
                    return;
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                String result = jedis.get("b");
                if (!"2".equals(result)) {
                    log.warn("Expect b to be 2 but found {}", result);
                    return;
                }
            }
        }).start();
        TimeUnit.SECONDS.sleep(5);
    }

    @GetMapping("/right")
    public void right() throws InterruptedException {

        new Thread(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                for (int i = 0; i < 1000; i++) {
                    String result = jedis.get("a");
                    if (!"1".equals(result)) {
                        log.warn("Expect a to be 1 but found {}", result);
                        return;
                    }
                }
            }
        }).start();
        new Thread(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                for (int i = 0; i < 1000; i++) {
                    String result = jedis.get("b");
                    if (!"2".equals(result)) {
                        log.warn("Expect b to be 2 but found {}", result);
                        return;
                    }
                }
            }
        }).start();
        TimeUnit.SECONDS.sleep(5);

    }

    @GetMapping("timeout")
    public String timeout(@RequestParam("waittimeout") int waittimeout,
                          @RequestParam("conntimeout") int conntimeout) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1);
        config.setMaxWaitMillis(waittimeout);
        try (JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379, conntimeout);
             Jedis jedis = jedisPool.getResource()) {
            return jedis.set("test", "test");
        }
    }
}
