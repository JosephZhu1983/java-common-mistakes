package org.geekbang.time.commonmistakes.nosqluse.influxdbvsmysql;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootApplication
@Slf4j
public class CommonMistakesApplication {

    //测试数据量
    public static final int ROWS = 10000000;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StandardEnvironment standardEnvironment;

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    @PostConstruct
    public void init() {
        //使用-Dspring.profiles.active=init启动程序进行初始化
        if (Arrays.stream(standardEnvironment.getActiveProfiles()).anyMatch(s -> s.equalsIgnoreCase("init"))) {
            initInfluxDB();
            initMySQL();
        }
    }

    //初始化MySQL
    private void initMySQL() {
        long begin = System.currentTimeMillis();
        jdbcTemplate.execute("DROP TABLE IF EXISTS `m`;");
        //只有ID、值和时间戳三列
        jdbcTemplate.execute("CREATE TABLE `m` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `value` bigint NOT NULL,\n" +
                "  `time` timestamp NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `time` (`time`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");

        String sql = "INSERT INTO `m` (`value`,`time`) VALUES (?,?)";
        //批量插入数据
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, ThreadLocalRandom.current().nextInt(10000));
                preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now().minusSeconds(5 * i)));
            }

            @Override
            public int getBatchSize() {
                return ROWS;
            }
        });
        log.info("init mysql finished with count {} took {}ms", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM `m`", Long.class), System.currentTimeMillis() - begin);
    }

    //初始化InfluxDB
    private void initInfluxDB() {
        long begin = System.currentTimeMillis();
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder()
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        try (InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086", "root", "root", okHttpClientBuilder)) {
            String db = "performance";
            influxDB.query(new Query("DROP DATABASE " + db));
            influxDB.query(new Query("CREATE DATABASE " + db));
            //设置数据库
            influxDB.setDatabase(db);
            //批量插入，10000条数据刷一次，或1秒刷一次
            influxDB.enableBatch(BatchOptions.DEFAULTS.actions(10000).flushDuration(1000));
            IntStream.rangeClosed(1, ROWS).mapToObj(i -> Point
                    .measurement("m")
                    .addField("value", ThreadLocalRandom.current().nextInt(10000))
                    .time(LocalDateTime.now().minusSeconds(5 * i).toInstant(ZoneOffset.UTC).toEpochMilli(), TimeUnit.MILLISECONDS).build())
                    .forEach(influxDB::write);
            influxDB.flush();
            log.info("init influxdb finished with count {} took {}ms", influxDB.query(new Query("SELECT COUNT(*) FROM m")).getResults().get(0).getSeries().get(0).getValues().get(0).get(1), System.currentTimeMillis() - begin);
        }
    }
}

