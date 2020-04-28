package org.geekbang.time.commonmistakes.springpart2.custompropertysource;

import lombok.extern.slf4j.Slf4j;
import org.geekbang.time.commonmistakes.common.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.StreamSupport;

@SpringBootApplication
@Slf4j
public class CommonMistakesApplication {

    private static final String MYSQL_URL_PLACEHOLDER = "%%MYSQL.URL%%";
    private static final String MYSQL_USERNAME_PLACEHOLDER = "%%MYSQL.USERNAME%%";
    private static final String MYSQL_PASSWORD_PLACEHOLDER = "%%MYSQL.PASSWORD%%";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        Utils.loadPropertySource(CommonMistakesApplication.class, "db.properties");
        new SpringApplicationBuilder()
                .sources(CommonMistakesApplication.class)
                .initializers(context -> initDbUrl(context.getEnvironment()))
                .run(args);
    }

    private static void initDbUrl(ConfigurableEnvironment env) {

        String dataSourceUrl = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");

        if (dataSourceUrl != null && !dataSourceUrl.contains(MYSQL_URL_PLACEHOLDER))
            throw new IllegalArgumentException("请使用占位符" + MYSQL_URL_PLACEHOLDER + "来替换数据库URL配置！");
        if (username != null && !username.contains(MYSQL_USERNAME_PLACEHOLDER))
            throw new IllegalArgumentException("请使用占位符" + MYSQL_USERNAME_PLACEHOLDER + "来替换数据库账号配置！");
        if (password != null && !password.contains(MYSQL_PASSWORD_PLACEHOLDER))
            throw new IllegalArgumentException("请使用占位符" + MYSQL_PASSWORD_PLACEHOLDER + "来替换数据库密码配置！");

        //这里我把值写死了，实际应用中可以从外部服务来获取
        Map<String, String> property = new HashMap<>();
        property.put(MYSQL_URL_PLACEHOLDER, "jdbc:mysql://localhost:6657/common_mistakes?characterEncoding=UTF-8&useSSL=false");
        property.put(MYSQL_USERNAME_PLACEHOLDER, "root");
        property.put(MYSQL_PASSWORD_PLACEHOLDER, "kIo9u7Oi0eg");

        Properties modifiedProps = new Properties();
        StreamSupport.stream(env.getPropertySources().spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
                .flatMap(Arrays::stream)
                .forEach(propKey -> {
                    String propValue = env.getProperty(propKey);
                    property.entrySet().forEach(item -> {
                        if (propValue.contains(item.getKey())) {
                            modifiedProps.put(propKey, propValue.replaceAll(item.getKey(), item.getValue()));
                        }
                    });
                });

        if (!modifiedProps.isEmpty()) {
            log.info("modifiedProps: {}", modifiedProps);
            env.getPropertySources().addFirst(new PropertiesPropertySource("mysql", modifiedProps));
        }
    }

    @Bean
    public CommandLineRunner checkDb() {
        return args -> {
            log.info("result {}", jdbcTemplate.queryForObject("SELECT NOW()", String.class));
        };
    }

}

