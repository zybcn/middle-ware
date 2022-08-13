package cn.zybcn.config;

import cn.zybcn.annnotation.AnnotationProcess;
import cn.zybcn.dto.AnnotationParam;
import cn.zybcn.service.AnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-13 17:42
 */
@Configuration
public class AnnotationConfig {


    private final Map<String, AnnotationService<AnnotationParam>> archiveServiceMap = new HashMap<>();

    /***
     * 服务启动的时候注册到我们自己定义的map中
     */
    @Autowired
    public void init(List<AnnotationService<AnnotationParam>> annotationServices) {
        for (AnnotationService<AnnotationParam> annotationService : annotationServices) {
            AnnotationProcess processValue = AnnotationUtils
                    .findAnnotation(annotationService.getClass(), AnnotationProcess.class);
            archiveServiceMap.put(processValue.value(), annotationService);
        }
    }

    public AnnotationService<AnnotationParam> getArchiveService(String type) {
        return archiveServiceMap.get(type);
    }
}
