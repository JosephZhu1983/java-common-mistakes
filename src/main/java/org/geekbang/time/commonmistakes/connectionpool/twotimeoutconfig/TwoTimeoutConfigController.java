package org.geekbang.time.commonmistakes.connectionpool.twotimeoutconfig;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

@RequestMapping("twotimeoutconfig")
@Slf4j
@RestController
public class TwoTimeoutConfigController {

    private static CloseableHttpClient httpClient = null;

    static {
        httpClient = HttpClients.createSystem();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                httpClient.close();
            } catch (IOException ignored) {
            }
        }));
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("mysql")
    public String mysql() {
        //调试StandardSocketFactory进行验证
        return jdbcTemplate.queryForObject("SELECT 'OK'", String.class);
    }

    @GetMapping("redis")
    public String redis() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1);
        config.setMaxWaitMillis(10000);
        try (JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379, 5000);
             Jedis jedis = jedisPool.getResource()) {
            return jedis.set("test", "test");
        }
    }

    @GetMapping("http")
    public String http() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(10000)
                .build();
        HttpGet httpGet = new HttpGet("http://127.0.0.1:45678/twotimeoutconfig/test");
        httpGet.setConfig(requestConfig);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            return EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }
}
