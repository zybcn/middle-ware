package cn.zybcn.idempotent;

import cn.zybcn.idempotent.aspect.IdempotentAspect;
import cn.zybcn.idempotent.expression.ExpressionResolver;
import cn.zybcn.idempotent.expression.KeyResolver;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-21 18:23
 * @Desc 幂等插件进行初始化
 */
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class IdempotentAutoConfiguration {


    /**
     * 切面 拦截处理所有 @Idempotent
     *
     * @return Aspect
     */
    @Bean
    public IdempotentAspect idempotentAspect() {
        return new IdempotentAspect();
    }

    /**
     * key 解析器
     *
     * @return KeyResolver
     */
    @Bean
    @ConditionalOnMissingBean(KeyResolver.class)
    public KeyResolver keyResolver() {
        return new ExpressionResolver();
    }

}
