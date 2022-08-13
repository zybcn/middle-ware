package cn.zybcn.service.impl;

import cn.zybcn.annnotation.AnnotationProcess;
import cn.zybcn.dto.AnnotationParam;
import cn.zybcn.service.AnnotationService;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-13 18:00
 */
@AnnotationProcess("taskProcessMessage")
public class TaskProcessMessage implements AnnotationService<AnnotationParam> {


    @Override
    public void process(AnnotationParam type) {

        System.out.println("发送短消息");

    }
}
