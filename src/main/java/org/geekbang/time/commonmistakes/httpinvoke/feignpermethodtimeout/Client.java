package org.geekbang.time.commonmistakes.httpinvoke.feignpermethodtimeout;

import feign.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "clientsdk")
public interface Client {
    @GetMapping("/feignpermethodtimeout/method1")
    String method1(Request.Options options);

    @GetMapping("/feignpermethodtimeout/method2")
    String method2(Request.Options options);
}
