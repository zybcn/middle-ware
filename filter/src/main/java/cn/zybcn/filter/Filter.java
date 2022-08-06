package cn.zybcn.filter;

import java.util.Collection;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-06 21:44
 */
public interface Filter<T> {


    /**
     * 过滤器定义
     *
     * @param chain
     * @param elements
     * @return
     */
    Collection<T> filter(Collection<T> elements, FilterChain<T> chain);

}
