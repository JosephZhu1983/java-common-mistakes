package org.geekbang.time.commonmistakes.asyncprocess.compensation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CommonMistakesApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

