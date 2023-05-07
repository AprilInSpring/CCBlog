package com.zxnk.util;

import com.zxnk.dao.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

//SecurityContext工具类
public class ReidsUtils {

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }

    /**
     * @return: java.lang.Boolean
     * @decription  判断当前用户是否为管理员
     * @date 2023/5/5 14:54
    */
    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    /**
     * @return: java.lang.Long
     * @decription 获取用户id
     * @date 2023/5/5 14:53
    */
    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}