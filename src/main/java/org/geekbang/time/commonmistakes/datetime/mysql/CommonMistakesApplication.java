package org.geekbang.time.commonmistakes.datetime.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class CommonMistakesApplication implements CommandLineRunner {

    @Autowired
    private TimeRepository timeRepository;

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        TimeEntity timeEntity = new TimeEntity();
//        timeEntity.setId(1L);
//        timeEntity.setMydatetime(LocalDateTime.now());
//        timeEntity.setMytimestamp(LocalDateTime.now());
//        timeRepository.save(timeEntity);
        log.info("{}", timeRepository.findAll());
    }
}

