package org.geekbang.time.commonmistakes.httpinvoke.ribbonretry.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SmsClient")
public interface SmsClient {

    @GetMapping("/ribbonretryissueserver/sms")
    void sendSmsWrong(@RequestParam("mobile") String mobile, @RequestParam("message") String message);

    @PostMapping("/ribbonretryissueserver/sms")
    void sendSmsRight(@RequestParam("mobile") String mobile, @RequestParam("message") String message);
}
