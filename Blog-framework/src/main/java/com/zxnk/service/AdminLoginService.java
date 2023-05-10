package com.zxnk.service;

import com.zxnk.entity.Menu;
import com.zxnk.entity.User;
import com.zxnk.util.ResponseResult;

import java.util.List;

//管理员登录服务接口
public interface AdminLoginService {
    ResponseResult adminLogin(User user);
    public ResponseResult getUserInfo(User user);

    //根据用户id查询当前用户可用的路由菜单，如果是超级管理员(id为1)，则直接返回全部可用的路由菜单
    public List<Menu> getRouterMenuTreeByUserId(Long userId);

    ResponseResult logout();
}
