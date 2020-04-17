package org.geekbang.time.commonmistakes.httpinvoke.feignpermethodtimeout;

import feign.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("feignpermethodtimeout")
@Slf4j
public class FeignPerMethodTimeoutController {

    @Autowired
    private Client client;

    @GetMapping("test")
    public void test() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("method1");
        String result1 = client.method1(new Request.Options(1000, 2500));
        stopWatch.stop();
        stopWatch.start("method2");
        String result2 = client.method2(new Request.Options(1000, 3500));
        stopWatch.stop();
        log.info("result1:{} result2:{} time:{}", result1, result2, stopWatch.prettyPrint());
    }

    @GetMapping("method1")
    public String method1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return "method1";
    }

    @GetMapping("method2")
    public String method2() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return "method1";
    }
}
