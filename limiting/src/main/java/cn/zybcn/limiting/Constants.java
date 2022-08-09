package cn.zybcn.limiting;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-09 21:21
 */
public class Constants {

    public static Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

}
