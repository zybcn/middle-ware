package cn.zybcn.idempotent.expression;

import cn.zybcn.idempotent.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-21 18:31
 */
public interface KeyResolver {

    /**
     * 解析处理 key
     *
     * @param idempotent 接口注解标识
     * @param point      接口切点信息
     * @return 处理结果
     */
    String resolver(Idempotent idempotent, JoinPoint point);
}
