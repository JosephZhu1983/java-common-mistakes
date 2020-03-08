package org.geekbang.time.commonmistakes.httpinvoke.ribbonretry;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableFeignClients(basePackages = "org.geekbang.time.commonmistakes.httpinvoke.ribbonretry.feign")
public class AutoConfig {
}
