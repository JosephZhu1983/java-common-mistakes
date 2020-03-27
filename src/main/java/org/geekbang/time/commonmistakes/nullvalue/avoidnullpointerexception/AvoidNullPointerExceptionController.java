package org.geekbang.time.commonmistakes.nullvalue.avoidnullpointerexception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("avoidnullpointerexception")
@Slf4j
public class AvoidNullPointerExceptionController {

    @GetMapping("wrong")
    public int wrong(@RequestParam(value = "test", defaultValue = "1111") String test) {
        return wrongMethod(test.charAt(0) == '1' ? null : new FooService(),
                test.charAt(1) == '1' ? null : 1,
                test.charAt(2) == '1' ? null : "OK",
                test.charAt(3) == '1' ? null : "OK").size();
    }

    @GetMapping("right")
    public int right(@RequestParam(value = "test", defaultValue = "1111") String test) {
        return Optional.ofNullable(rightMethod(test.charAt(0) == '1' ? null : new FooService(),
                test.charAt(1) == '1' ? null : 1,
                test.charAt(2) == '1' ? null : "OK",
                test.charAt(3) == '1' ? null : "OK"))
                .orElse(Collections.emptyList()).size();
    }


    private List<String> wrongMethod(FooService fooService, Integer i, String s, String t) {
        log.info("result {} {} {} {}", i + 1, s.equals("OK"), s.equals(t),
                new ConcurrentHashMap<String, String>().put(null, null));
        if (fooService.getBarService().bar().equals("OK"))
            log.info("OK");
        return null;
    }

    private List<String> rightMethod(FooService fooService, Integer i, String s, String t) {
        log.info("result {} {} {} {}", Optional.ofNullable(i).orElse(0) + 1, "OK".equals(s), Objects.equals(s, t), new HashMap<String, String>().put(null, null));
        Optional.ofNullable(fooService)
                .map(FooService::getBarService)
                .filter(barService -> "OK".equals(barService.bar()))
                .ifPresent(result -> log.info("OK"));
        return new ArrayList<>();
    }

    class FooService {
        @Getter
        private BarService barService;

    }

    class BarService {
        String bar() {
            return "OK";
        }
    }
}
