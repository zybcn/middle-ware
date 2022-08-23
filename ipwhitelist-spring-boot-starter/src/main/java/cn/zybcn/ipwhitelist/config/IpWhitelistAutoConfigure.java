package cn.zybcn.ipwhitelist.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-23 21:13
 */


@EnableConfigurationProperties(IpWhitelistConfig.class)
public class IpWhitelistAutoConfigure {

    @Bean
    @ConditionalOnMissingBean
    public IpWhitelistWebMvcConfigurer ipWhitelistWebMvcConfigurer() {
        return new IpWhitelistWebMvcConfigurer();
    }
}
