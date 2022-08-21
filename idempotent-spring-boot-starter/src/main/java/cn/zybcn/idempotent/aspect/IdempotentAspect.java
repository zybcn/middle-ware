package cn.zybcn.idempotent.aspect;

import cn.zybcn.idempotent.Constant;
import cn.zybcn.idempotent.annotation.Idempotent;
import cn.zybcn.idempotent.exception.IdempotentException;
import cn.zybcn.idempotent.expression.KeyResolver;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-21 18:16
 */

@Aspect
public class IdempotentAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdempotentAspect.class);

    private static final ThreadLocal<Map<String, Object>> THREAD_CACHE = ThreadLocal.withInitial(HashMap::new);


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KeyResolver keyResolver;

    @Pointcut("@annotation(cn.zybcn.idempotent.annotation.Idempotent)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void beforePointCut(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (!method.isAnnotationPresent(Idempotent.class)) {
            return;
        }
        Idempotent idempotent = method.getAnnotation(Idempotent.class);

        String key;

        // 若没有配置 幂等 标识编号，则使用 url + 参数列表作为区分
        if (!StringUtils.hasLength(idempotent.key())) {
            String url = request.getRequestURL().toString();
            String argString = Arrays.asList(joinPoint.getArgs()).toString();
            key = url + argString;
        } else {
            // 使用jstl 规则区分
            key = keyResolver.resolver(idempotent, joinPoint);
        }
        // 当配置了el表达式但是所选字段为空时,会抛出异常,兜底使用url做标识
        if (key == null) {
            key = request.getRequestURL().toString();
        }

        long expireTime = idempotent.expireTime();
        String info = idempotent.info();
        TimeUnit timeUnit = idempotent.timeUnit();
        boolean delKey = idempotent.delKey();

        String value = LocalDateTime.now().toString().replace("T", " ");
        Boolean state = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, timeUnit);
        if (state == null || !state) {
            throw new IdempotentException(info);
        } else {
            LOGGER.info("[idempotent]:has stored key={},value={},expireTime={}{},now={}", key, value, expireTime,
                    timeUnit, LocalDateTime.now().toString());
        }
        Map<String, Object> map = THREAD_CACHE.get();
        map.put(Constant.KEY, key);
        map.put(Constant.DEL_KEY, delKey);
    }

    @After("pointCut()")
    public void afterPointCut(JoinPoint joinPoint) {
        Map<String, Object> map = THREAD_CACHE.get();
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        String key = map.get(Constant.KEY).toString();
        boolean delKey = (boolean) map.get(Constant.DEL_KEY);
        if (delKey) {
            redisTemplate.delete(key);
            LOGGER.info("[idempotent]:has removed key={}", key);
        }
        THREAD_CACHE.remove();
    }


}
