package org.geekbang.time.commonmistakes.troubleshootingtools.mat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@Slf4j
public class JMapApplication implements CommandLineRunner {

    //-Xmx512m -Xms512m
    public static void main(String[] args) {
        SpringApplication.run(JMapApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            String payload = IntStream.rangeClosed(1, 1000000)
                    .mapToObj(__ -> "a")
                    .collect(Collectors.joining("")) + UUID.randomUUID().toString();
            log.debug(payload);
            TimeUnit.MILLISECONDS.sleep(1);
        }
    }
}
