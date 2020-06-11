package org.geekbang.time.commonmistakes.transaction.transactionproxyfailed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class CommonMistakesApplication {

    public static void main(String[] args) {
        //System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, ".");

        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

