package cn.zybcn.startategy;

import cn.zybcn.filter.Filter;
import cn.zybcn.filter.FilterChain;

import java.util.*;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-06 21:55
 * @Desc 过滤器链
 */
public class DefaultFilterChain<T> implements FilterChain<T> {

    private Map<String, Object> context;

    private List<Filter<T>> filters;
    /**
     * 记录过滤器的位置，当前执行到的过滤器
     */
    private int index;


    public DefaultFilterChain(List<Filter<T>> filters) {
        this.filters = filters;
        this.index = 0;
        this.context = new HashMap<>();
    }

    public DefaultFilterChain(List<Filter<T>> filters, Map<String, Object> context) {
        this.filters = filters;
        this.index = 0;
        this.context = Optional.ofNullable(context).orElseGet(HashMap::new);
    }

    public DefaultFilterChain(DefaultFilterChain<T> parent, int index) {
        this.filters = parent.getFilters();
        this.index = index;
        this.context = parent.getContext();
    }

    @Override
    public Map<String, Object> getContext() {
        return context;
    }

    public List<Filter<T>> getFilters() {
        return filters;
    }


    @Override
    public Collection<T> filter(Collection<T> elements) {
        if (this.index < filters.size()) {
            Filter<T> filter = filters.get(this.index);
            DefaultFilterChain<T> chain = new DefaultFilterChain<>(this, this.index + 1);
            return filter.filter(elements, chain);
        } else {
            return elements;
        }
    }


    /**
     * 执行过滤操作
     */
    public Collection<T> handle(Collection<T> elements) {
        return new DefaultFilterChain<>(filters).filter(elements);
    }

    /**
     * 执行过滤操作
     */
    public Collection<T> handle(Collection<T> elements, Map<String, Object> context) {
        return new DefaultFilterChain<>(filters, context).filter(elements);
    }

}
