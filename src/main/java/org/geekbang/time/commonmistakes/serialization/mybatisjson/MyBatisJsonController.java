package org.geekbang.time.commonmistakes.serialization.mybatisjson;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("mybatisjson")
@Slf4j
@RestController
public class MyBatisJsonController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JsonTestMapper jsonTestMapper;

    @ExceptionHandler
    public void handle(HttpServletRequest req, HandlerMethod method, Exception ex) {
        log.warn(String.format("访问 %s -> %s 出现异常！", req.getRequestURI(), method.toString()), ex);
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("drop table IF EXISTS `jsontest`;");
        jdbcTemplate.execute("CREATE TABLE `jsontest` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                "  `info` json DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;");
        jdbcTemplate.execute("INSERT INTO `jsontest` (info) VALUES ('[{\"name\":\"zhuye\",\"phone\":\"136511112222\"}]')");
    }

    @GetMapping("wrong")
    public List<JsonTest> wrong() {
        List<JsonTest> result = jsonTestMapper.selectList(new QueryWrapper<>());
        log.info("result: {}", result);
        result.stream().flatMap(jsonTest -> jsonTest.getInfowrong().stream())
                .forEach(info -> log.info("type:{}", info.getClass()));
        return result;
    }

    @GetMapping("right")
    public List<JsonTest> right() {
        List<JsonTest> result = jsonTestMapper.selectList(new QueryWrapper<>());
        log.info("result: {}", result);
        result.stream().flatMap(jsonTest -> jsonTest.getInforight().stream())
                .forEach(info -> log.info("type:{}", info.getClass()));
        return result;
    }
}
