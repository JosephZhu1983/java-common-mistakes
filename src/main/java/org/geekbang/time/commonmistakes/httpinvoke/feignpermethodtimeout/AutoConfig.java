package org.geekbang.time.commonmistakes.httpinvoke.feignpermethodtimeout;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "org.geekbang.time.commonmistakes.httpinvoke.feignpermethodtimeout")
public class AutoConfig {
}
