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
    ;

    @Autowired
    private TestService testService;

    @GetMapping("test1")
    public Long test1() {
        return testService.test1();
    }

    @GetMapping("test2")
    public Long test2() {
        return testService.test2();
    }

    @GetMapping("test3")
    public Long test3() {
        return testService.test3();
    }
}
