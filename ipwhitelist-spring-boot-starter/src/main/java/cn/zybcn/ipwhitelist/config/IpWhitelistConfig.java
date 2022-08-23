package cn.zybcn.ipwhitelist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-23 21:11
 */
@Configuration
@ConfigurationProperties(prefix = "white")
public class IpWhitelistConfig {

    private boolean enable;

    private String[] allow;

    private String[] includePatterns;

    private String[] excludePatterns;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String[] getAllow() {
        return allow;
    }

    public void setAllow(String[] allow) {
        this.allow = allow;
    }

    public String[] getIncludePatterns() {
        return includePatterns;
    }

    public void setIncludePatterns(String[] includePatterns) {
        this.includePatterns = includePatterns;
    }

    public String[] getExcludePatterns() {
        return excludePatterns;
    }

    public void setExcludePatterns(String[] excludePatterns) {
        this.excludePatterns = excludePatterns;
    }
}
