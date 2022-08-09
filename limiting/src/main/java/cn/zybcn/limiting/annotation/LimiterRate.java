package cn.zybcn.limiting.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangyibo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LimiterRate {

    /**
     * 限流许可量
     */
    long permitsPerSecond() default 0L;

    /**
     * 失败返回结果
     */
    String returnJson() default "";
}
