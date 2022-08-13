package cn.zybcn.service.impl;

import cn.zybcn.annnotation.AnnotationProcess;
import cn.zybcn.dto.AnnotationParam;
import cn.zybcn.service.AnnotationService;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-13 18:01
 */
@AnnotationProcess("takProcessWechat")
public class TaskProcessWechat  implements AnnotationService<AnnotationParam> {
    @Override
    public void process(AnnotationParam type) {
        System.out.println("发送微信消息");
    }
}
