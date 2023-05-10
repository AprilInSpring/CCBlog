package com.zxnk.service.impl;

import com.zxnk.entity.LoginUser;
import com.zxnk.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PermissionsService
 * @Description 权限校验服务类
 * @Author cc
 * @Date 2023/5/9 21:25
 * @Version 1.0
 */
@Service("permissions")
public class PermissionsService {

    /**
     * @param permission 权限
     * @return: boolean  是否具有权限
     * @decription 判断当前用户是否具有该权限
     * @date 2023/5/9 21:35
    */
    public boolean hasAnyAuthorities(String permission){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //如果是超级管理员，直接返回true
        if(loginUser.getUser().getId().equals(1L)){
            return true;
        }else {
            //获取当前用户的权限列表，判断是否拥有该权限
            List<String> permissions = loginUser.getPermissions();
            return permissions.contains(permission);
        }
    }
}