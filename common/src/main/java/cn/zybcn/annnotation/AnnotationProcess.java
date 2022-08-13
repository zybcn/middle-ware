package cn.zybcn.annnotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author zhangyibo
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface AnnotationProcess {

    String value();
}
