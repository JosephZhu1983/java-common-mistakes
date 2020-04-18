package org.geekbang.time.commonmistakes.troubleshootingtools.wireshark;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class BatchInsertApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(BatchInsertApplication.class, args);
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("drop table IF EXISTS `testuser`;");
        jdbcTemplate.execute("create TABLE `testuser` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(255) NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
    }

    @Override
    public void run(String... args) {

        long begin = System.currentTimeMillis();
        String sql = "INSERT INTO `testuser` (`name`) VALUES (?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, "usera" + i);
            }

            @Override
            public int getBatchSize() {
                return 10000;
            }
        });
        log.info("took : {} ms", System.currentTimeMillis() - begin);
    }
}
