package org.geekbang.time.commonmistakes.logging.duplicate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        System.setProperty("logging.config", "classpath:org/geekbang/time/commonmistakes/logging/duplicate/multiplelevelsfilter.xml");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

