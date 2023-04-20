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
    public String test3() throws InterruptedException {
        return testService.redisTemplateNotInTransactional();
    }


    @GetMapping("test4")
    public String test4() throws InterruptedException {
        return testService.redisTemplateInTransactional();
    }

    @GetMapping("test5")
    public String test5() {
        return testService.multiTestCallback();
    }

    @GetMapping("test6")
    public String test6() {
        return testService.multiTestInTransactional() + testService.counter();
    }
}
