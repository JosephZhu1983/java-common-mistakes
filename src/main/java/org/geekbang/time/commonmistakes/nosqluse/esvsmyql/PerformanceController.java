package org.geekbang.time.commonmistakes.nosqluse.esvsmyql;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@RestController
@Slf4j
@RequestMapping("esvsmysql")
public class PerformanceController {
    @Autowired
    private NewsESRepository newsESRepository;
    @Autowired
    private NewsMySQLRepository newsMySQLRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("mysql")
    public void mysql(@RequestParam(value = "cateid", defaultValue = "1") int cateid,
                      @RequestParam(value = "keyword1", defaultValue = "社会") String keyword1,
                      @RequestParam(value = "keyword2", defaultValue = "苹果") String keyword2) {
        long begin = System.currentTimeMillis();
        Object result = newsMySQLRepository.countByCateidAndContentContainingAndContentContaining(cateid, keyword1, keyword2);
        log.info("took {} ms result {}", System.currentTimeMillis() - begin, result);
    }

    @GetMapping("es")
    public void es(@RequestParam(value = "cateid", defaultValue = "1") int cateid,
                   @RequestParam(value = "keyword1", defaultValue = "社会") String keyword1,
                   @RequestParam(value = "keyword2", defaultValue = "苹果") String keyword2) {
        long begin = System.currentTimeMillis();
        Object result = newsESRepository.countByCateidAndContentContainingAndContentContaining(cateid, keyword1, keyword2);
        log.info("took {} ms result {}", System.currentTimeMillis() - begin, result);
    }

    @GetMapping("mysql2")
    public void mysql2(@RequestParam(value = "id", defaultValue = "400000") long id) {
        long begin = System.currentTimeMillis();
        //对于MySQL，使用JdbcTemplate+SQL语句实现直接更新某个category字段，更新1000次
        IntStream.rangeClosed(1, 1000).forEach(i -> {
            jdbcTemplate.update("UPDATE `news` SET category=? WHERE id=?", new Object[]{"test" + i, id});
        });
        log.info("mysql took {} ms result {}", System.currentTimeMillis() - begin, newsMySQLRepository.findById(id));

    }

    @GetMapping("es2")
    public void es(@RequestParam(value = "id", defaultValue = "400000") long id) {
        long begin = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1000).forEach(i -> {
            //对于ES，通过ElasticsearchTemplate+自定义UpdateQuery实现文档的部分更新
            UpdateQuery updateQuery = null;
            try {
                updateQuery = new UpdateQueryBuilder()
                        .withIndexName("news")
                        .withId(String.valueOf(id))
                        .withType("_doc")
                        .withUpdateRequest(new UpdateRequest().doc(
                                jsonBuilder()
                                        .startObject()
                                        .field("category", "test" + i)
                                        .endObject()))
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            elasticsearchTemplate.update(updateQuery);
        });
        log.info("es took {} ms result {}", System.currentTimeMillis() - begin, newsESRepository.findById(id).get());
    }
}
