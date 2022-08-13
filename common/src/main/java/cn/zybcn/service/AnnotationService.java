package cn.zybcn.service;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-13 18:07
 */
public interface AnnotationService<P> {

    /**
     * 不同服务的处理方法
     */
    void process(P param);
}
