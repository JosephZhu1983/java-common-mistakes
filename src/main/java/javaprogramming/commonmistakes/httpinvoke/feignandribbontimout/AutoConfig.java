package javaprogramming.commonmistakes.httpinvoke.feignandribbontimout;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "javaprogramming.commonmistakes.httpinvoke.feignandribbontimout")
public class AutoConfig {
}
