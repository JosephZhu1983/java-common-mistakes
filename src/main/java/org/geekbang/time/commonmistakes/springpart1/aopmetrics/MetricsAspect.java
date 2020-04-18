package org.geekbang.time.commonmistakes.springpart1.aopmetrics;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Aspect
@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MetricsAspect {
    private static final Map<Class<?>, Object> DEFAULT_VALUES = Stream
            .of(boolean.class, byte.class, char.class, double.class, float.class, int.class, long.class, short.class)
            .collect(toMap(clazz -> (Class<?>) clazz, clazz -> Array.get(Array.newInstance(clazz, 1), 0)));
    @Autowired
    private ObjectMapper objectMapper;

    public static <T> T getDefaultValue(Class<T> clazz) {
        return (T) DEFAULT_VALUES.get(clazz);
    }

//    @Pointcut("@annotation(org.geekbang.time.commonmistakes.spring.demo2.Metrics)")
//    public void withMetricsAnnotation() {
//    }

    @Pointcut("within(@org.geekbang.time.commonmistakes.springpart1.aopmetrics.Metrics *)")
    public void withMetricsAnnotation() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerBean() {
    }

    @Around("controllerBean() || withMetricsAnnotation())")
    public Object metrics(ProceedingJoinPoint pjp) throws Throwable {
        //尝试获取当前方法的类名和方法名
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String name = String.format("【%s】【%s】", signature.getDeclaringType().toString(), signature.toLongString());

        Metrics metrics = signature.getMethod().getAnnotation(Metrics.class);
        if (metrics == null) {
            metrics = signature.getMethod().getDeclaringClass().getAnnotation(Metrics.class);
        }
        //对于Controller和Repository，我们需要初始化一个@Metrics注解出来
        if (metrics == null) {
            @Metrics
            final class c {
            }
            metrics = c.class.getAnnotation(Metrics.class);
        }
        //对于Web项目我们可以从上下文中获取到额外的一些信息来丰富我们的日志
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (request != null)
                name += String.format("【%s】", request.getRequestURL().toString());
        }
        //实现的是入参的日志输出
        if (metrics.logParameters())
            log.info(String.format("【入参日志】调用 %s 的参数是：【%s】", name, objectMapper.writeValueAsString(pjp.getArgs())));
        //实现连接点方法的执行，以及成功失败的打点，出现异常的时候还会记录日志
        //这里我们通过日志方式暂时替代了打点的实现，标准的实现是需要把信息对接打点服务，比如Micrometer
        Object returnValue;
        Instant start = Instant.now();
        try {
            returnValue = pjp.proceed();
            if (metrics.recordSuccessMetrics())
                log.info(String.format("【成功打点】调用 %s 成功，耗时：%d ms", name, Duration.between(start, Instant.now()).toMillis()));
        } catch (Exception ex) {
            if (metrics.recordFailMetrics())
                log.info(String.format("【失败打点】调用 %s 失败，耗时：%d ms", name, Duration.between(start, Instant.now()).toMillis()));
            if (metrics.logException())
                log.error(String.format("【异常日志】调用 %s 出现异常！", name), ex);

            //如果忽略异常那么直接返回默认值
            if (metrics.ignoreException())
                returnValue = getDefaultValue(signature.getReturnType());
            else
                throw ex;
        }
        //实现了返回值的日志输出
        if (metrics.logReturn())
            log.info(String.format("【出参日志】调用 %s 的返回是：【%s】", name, returnValue));
        return returnValue;
    }
}