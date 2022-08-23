package cn.zybcn.ipwhitelist.interceptor;

import cn.zybcn.ipwhitelist.config.IpWhitelistConfig;
import com.pongsky.kit.ip.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author ZhangYiBo
 * @Date 2022-08-23 21:17
 */
@AutoConfigureAfter(IpWhitelistConfig.class)
public class IpWhitelistInterceptor implements HandlerInterceptor {
    private boolean ipWhitelistIsEnable;
    private Set<String> ipWhitelistAllowList;


    private IpWhitelistConfig ipWhitelistConfig;

    /**
     * 构造方法 初始化数据
     */
    public IpWhitelistInterceptor(IpWhitelistConfig ipWhitelistConfig) {
        this.ipWhitelistConfig = ipWhitelistConfig;
        if (!this.ipWhitelistConfig.isEnable()) {
            this.ipWhitelistIsEnable = false;
            return;
        }
        if (this.ipWhitelistConfig.getAllow() == null || this.ipWhitelistConfig.getAllow().length == 0) {
            this.ipWhitelistIsEnable = false;
            return;
        }
        this.ipWhitelistIsEnable = true;
        if (this.ipWhitelistConfig.getAllow() != null) {
            this.ipWhitelistAllowList = new HashSet(Arrays.asList(this.ipWhitelistConfig.getAllow()));
        } else {
            this.ipWhitelistAllowList = Collections.emptySet();
        }

    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!ipWhitelistIsEnable) {
            return true;
        }
        String ip = IpUtils.getIp(request);
        boolean allow = ipWhitelistAllowList.contains(ip);
        if (!allow) {
            try {
                response.sendError(401, "无权限");
            } catch (IOException ignored) {
            }
        }
        return allow;
    }


}
