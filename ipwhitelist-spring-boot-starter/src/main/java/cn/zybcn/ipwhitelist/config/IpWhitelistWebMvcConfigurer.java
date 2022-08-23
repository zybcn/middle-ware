package cn.zybcn.ipwhitelist.config;

import cn.zybcn.ipwhitelist.interceptor.IpWhitelistInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-23 21:34
 */

public class IpWhitelistWebMvcConfigurer implements WebMvcConfigurer {


    @Autowired
    private IpWhitelistConfig ipWhitelistConfig;

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    /**
     * addPathPatterns 添加拦截规则
     * excludePathPatterns 排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 白名单配置
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new IpWhitelistInterceptor(ipWhitelistConfig));
        if (this.ipWhitelistConfig.getIncludePatterns() != null) {
            interceptorRegistration.addPathPatterns(this.ipWhitelistConfig.getIncludePatterns());
        }
        if (this.ipWhitelistConfig.getExcludePatterns() != null) {
            interceptorRegistration.excludePathPatterns(this.ipWhitelistConfig.getExcludePatterns());
        }
    }
}
