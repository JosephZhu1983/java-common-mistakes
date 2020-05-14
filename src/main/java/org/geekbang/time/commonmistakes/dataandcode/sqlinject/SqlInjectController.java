package org.geekbang.time.commonmistakes.dataandcode.sqlinject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("sqlinject")
@Slf4j
@RestController
public class SqlInjectController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDataMapper userDataMapper;

    @ExceptionHandler
    public void handle(HttpServletRequest req, HandlerMethod method, Exception ex) {
        log.warn(String.format("访问 %s -> %s 出现异常！", req.getRequestURI(), method.toString()), ex);
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("drop table IF EXISTS `userdata`;");
        jdbcTemplate.execute("create TABLE `userdata` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(255) NOT NULL,\n" +
                "  `password` varchar(255) NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
        jdbcTemplate.execute("INSERT INTO `userdata` (name,password) VALUES ('test1','haha1'),('test2','haha2')");
    }

    @PostMapping("jdbcwrong")
    public void jdbcwrong(@RequestParam("name") String name) {
        //curl -X POST http://localhost:45678/sqlinject/jdbcwrong\?name\=test
        //python sqlmap.py -u  http://localhost:45678/sqlinject/jdbcwrong --data name=test --current-db --flush-session
        //python sqlmap.py -u  http://localhost:45678/sqlinject/jdbcwrong --data name=test --tables -D "common_mistakes"
        //python sqlmap.py -u  http://localhost:45678/sqlinject/jdbcwrong --data name=test -D "common_mistakes" -T "userdata" --dump
        log.info("{}", jdbcTemplate.queryForList("SELECT id,name FROM userdata WHERE name LIKE '%" + name + "%'"));
    }

    @PostMapping("jdbcright")
    public void jdbcright(@RequestParam("name") String name) {
        //curl -X POST http://localhost:45678/sqlinject/jdbcright\?name\=test
        log.info("{}", jdbcTemplate.queryForList("SELECT id,name FROM userdata WHERE name LIKE ?", "%" + name + "%"));
    }

    @PostMapping("mybatiswrong")
    public List mybatiswrong(@RequestParam("name") String name) {
        //curl -X POST http://localhost:45678/sqlinject/mybatiswrong\?name\=test
        //python sqlmap.py -u  http://localhost:45678/sqlinject/mybatiswrong --data name=test --current-db --flush-session
        return userDataMapper.findByNameWrong(name);
    }

    @PostMapping("mybatisright")
    public List mybatisright(@RequestParam("name") String name) {
        //curl -X POST http://localhost:45678/sqlinject/mybatisright?name\=test
        return userDataMapper.findByNameRight(name);
    }

    @PostMapping("mybatiswrong2")
    public List mybatiswrong2(@RequestParam("names") String names) {
        //curl -X POST http://localhost:45678/sqlinject/mybatiswrong2\?names\=\'test1\',\'test2\'
        //python sqlmap.py -u  http://localhost:45678/sqlinject/mybatiswrong2 --data names="'test1','test2'"
        return userDataMapper.findByNamesWrong(names);
    }

    @PostMapping("mybatisright2")
    public List mybatisright2(@RequestParam("names") List<String> names) {
        //curl -X POST http://localhost:45678/sqlinject/mybatisright2\?names\=test1,test2
        return userDataMapper.findByNamesRight(names);
    }
}
