package javaprogramming.commonmistakes.httpinvoke.ribbonretry;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableFeignClients(basePackages = "javaprogramming.commonmistakes.httpinvoke.ribbonretry.feign")
public class AutoConfig {
}
