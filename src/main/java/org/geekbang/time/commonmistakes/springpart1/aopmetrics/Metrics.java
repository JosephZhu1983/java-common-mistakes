package org.geekbang.time.commonmistakes.springpart1.aopmetrics;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Metrics {
    /**
     * 是否在成功执行方法后打点
     *
     * @return
     */
    boolean recordSuccessMetrics() default true;

    /**
     * 是否在执行方法出错时打点
     *
     * @return
     */
    boolean recordFailMetrics() default true;

    /**
     * 是否记录请求参数
     *
     * @return
     */
    boolean logParameters() default true;

    /**
     * 是否记录返回值
     *
     * @return
     */
    boolean logReturn() default true;

    /**
     * 是否记录异常
     *
     * @return
     */
    boolean logException() default true;

    /**
     * 是否屏蔽异常返回默认值
     *
     * @return
     */
    boolean ignoreException() default false;
}
