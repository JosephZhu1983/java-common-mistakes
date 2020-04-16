package org.geekbang.time.commonmistakes.datetime.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;


@SpringBootApplication
@Slf4j
public class CommonMistakesApplication implements CommandLineRunner {

    @Autowired
    private TimeRepository timeRepository;

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        TimeEntity timeEntity = new TimeEntity();
//        timeEntity.setId(1L);
//        timeEntity.setMydatetime1(LocalDateTime.now());
//        timeEntity.setMytimestamp1(LocalDateTime.now());
//        timeEntity.setMydatetime2(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("America/New_York")));
//        timeEntity.setMytimestamp2(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("America/New_York")));
//        timeEntity.setMydatetime3(LocalDateTime.now().toString());
//        timeEntity.setMytimestamp3(LocalDateTime.now().toString());
//        timeRepository.save(timeEntity);
        log.info("{}", timeRepository.findById(1L).get());
    }
}

