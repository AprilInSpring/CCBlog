package com.zxnk.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName CommonUaUtil
 * @Description TODO
 * @Author cc
 * @Date 2023/6/27 11:34
 * @Version 1.0
 */
public class CommonUaUtil {

    private static final String DASHED = "-";
    /**
     * 获取客户端浏览器
     *
     * @author xuyuxiang
     * @date 2020/3/19 14:53
     */
    public static String getBrowser(HttpServletRequest request) {
        //获取代理请求头
        UserAgent userAgent = getUserAgent(request);
        if (ObjectUtil.isEmpty(userAgent)) {
            return DASHED;
        } else {
            //获取浏览器类型
            String browser = userAgent.getBrowser().toString();
            return "Unknown".equals(browser) ? DASHED : browser;
        }
    }

    /**
     * 获取客户端操作系统
     *
     * @author xuyuxiang
     * @date 2022/9/2 15:36
     */
    public static String getOs(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        if (ObjectUtil.isEmpty(userAgent)) {
            return DASHED;
        } else {
            //获取操作系统
            String os = userAgent.getOs().toString();
            return "Unknown".equals(os) ? DASHED : os;
        }
    }

    /**
     * 获取请求代理头
     *
     * @author xuyuxiang
     * @date 2022/9/2 15:36
     */
    private static UserAgent getUserAgent(HttpServletRequest request) {
        String userAgentStr = ServletUtil.getHeaderIgnoreCase(request, "User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        if (ObjectUtil.isNotEmpty(userAgentStr)) {
            if ("Unknown".equals(userAgent.getBrowser().getName())) {
                userAgent.setBrowser(new Browser(userAgentStr, null, ""));
            }
        }
        return userAgent;
    }
}