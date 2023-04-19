package org.geekbang.time.commonmistakes.cachedesign.redistransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("redistemplatetransaction")
@RestController
public class RedisTemplateTransactionController {

    @Autowired
    private TestService testService;

    @GetMapping("test1")
    public String test1() {
        return testService.multiTest();
    }

    @GetMapping("test2")
    public String test2() {
        return testService.noMultiTest();
    }

    @GetMapping("test3")
    public String test3() {
        return testService.redisTemplateInTransactional();
    }

    @GetMapping("test4")
    public String test4() {
        return testService.multiTestInTransactional();
    }
}
