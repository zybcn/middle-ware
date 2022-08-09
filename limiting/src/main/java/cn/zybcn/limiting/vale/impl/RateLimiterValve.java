package cn.zybcn.limiting.vale.impl;

import cn.zybcn.limiting.Constants;
import cn.zybcn.limiting.annotation.LimiterRate;
import cn.zybcn.limiting.vale.IValveService;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-09 21:26
 */
@Repository
public class RateLimiterValve implements IValveService {


    @Override
    public Object access(ProceedingJoinPoint jp, Method method, LimiterRate limiterRate) throws Throwable {

        if (0 == limiterRate.permitsPerSecond()) {
            return jp.proceed();
        }
        String clazzName = jp.getTarget().getClass().getName();
        String methodName = method.getName();
        String key = clazzName + "." + methodName;

        if (Constants.rateLimiterMap.get(key) == null) {
            Constants.rateLimiterMap.put(key, RateLimiter.create(limiterRate.permitsPerSecond()));
        }
        RateLimiter rateLimiter = Constants.rateLimiterMap.get(key);
        if (rateLimiter.tryAcquire()) {
            return jp.proceed();
        }
        return JSON.parseObject(limiterRate.returnJson(), method.getReturnType());
    }
}
