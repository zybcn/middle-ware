package cn.zybcn.limiting.vale;

import cn.zybcn.limiting.annotation.LimiterRate;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-09 21:25
 */
public interface IValveService {

    Object access(ProceedingJoinPoint jp, Method method, LimiterRate limiterRate) throws Throwable;
}
