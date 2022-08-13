package cn.zybcn.service.impl;

import cn.zybcn.config.AnnotationConfig;
import cn.zybcn.dto.AnnotationParam;
import cn.zybcn.service.AnnotationService;
import cn.zybcn.service.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-13 18:11
 */
@Service
public class TaskListenerService implements Listener {


    @Autowired
    private AnnotationConfig annotationConfig;

    @Override
    public void receiver(AnnotationParam param) {

        Assert.notNull(param, "传递参数不能为空");
        Assert.notNull(param.getBizType(), "传递调用的服务的类型不能为空");

        AnnotationService<AnnotationParam> archiveService = annotationConfig.getArchiveService(param.getBizType());

        if (archiveService != null) {
            // 根据type选择对应服务处理
            archiveService.process(param);
        }

    }
}
