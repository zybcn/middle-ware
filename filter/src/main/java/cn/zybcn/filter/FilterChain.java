package cn.zybcn.filter;

import java.util.Collection;
import java.util.Map;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-06 21:45
 * @Desc 过滤链
 */
public interface FilterChain<T> {


    /**
     * 过滤集合中的元素
     *
     * @param elements 元素集合
     * @return
     */
    Collection<T> filter(Collection<T> elements);


    /**
     * 获取上下文
     *
     * @return 上下文
     */
    Map<String, Object> getContext();


}
