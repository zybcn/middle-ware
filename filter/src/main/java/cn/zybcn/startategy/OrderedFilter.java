package cn.zybcn.startategy;

import cn.zybcn.filter.Filter;
import cn.zybcn.filter.FilterChain;
import org.springframework.core.Ordered;

import java.util.Collection;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-06 22:35
 */
public class OrderedFilter<T> implements Filter<T>, Ordered {


    private Filter<T> delegate;

    private final int order;

    public OrderedFilter(Filter<T> delegate, int order) {
        this.delegate = delegate;
        this.order = order;
    }

    @Override
    public Collection<T> filter(Collection<T> elements, FilterChain<T> chain) {
        return this.delegate.filter(elements, chain);
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public Filter<T> getDelegate() {
        return delegate;
    }
}
