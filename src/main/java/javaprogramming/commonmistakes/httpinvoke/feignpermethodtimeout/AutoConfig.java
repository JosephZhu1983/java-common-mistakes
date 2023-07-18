package javaprogramming.commonmistakes.httpinvoke.feignpermethodtimeout;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "javaprogramming.commonmistakes.httpinvoke.feignpermethodtimeout")
public class AutoConfig {
}
