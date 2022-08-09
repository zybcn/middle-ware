package cn.zybcn.limiting;

import cn.zybcn.limiting.annotation.LimiterRate;
import cn.zybcn.limiting.vale.IValveService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-09 21:11
 */
@Aspect
@Component
public class DoRateLimiterPoint {

    @Autowired
    private IValveService iValveService;

    @Pointcut("@annotation(cn.zybcn.limiting.annotation.LimiterRate)")
    public void aopPoint() {
    }


    @Around("aopPoint() && @annotation(limiterRate)")
    public Object doRouter(ProceedingJoinPoint jp, LimiterRate limiterRate) throws Throwable {
        return iValveService.access(jp, getMethod(jp), limiterRate);
    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

}
