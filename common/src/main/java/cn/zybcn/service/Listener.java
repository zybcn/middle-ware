package cn.zybcn.service;

import cn.zybcn.dto.AnnotationParam;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-13 17:56
 */
public interface Listener {
    /**
     * 处理的业务
     *
     * @param param 传递的业务参数
     */
    void receiver(AnnotationParam param);
}
