package org.geekbang.time.commonmistakes.connectionpool.datasource;

import org.geekbang.time.commonmistakes.common.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonMistakesApplicationGood {

    public static void main(String[] args) {

        Utils.loadPropertySource(CommonMistakesApplicationGood.class, "good.properties");
        SpringApplication.run(CommonMistakesApplicationGood.class, args);
    }
}

