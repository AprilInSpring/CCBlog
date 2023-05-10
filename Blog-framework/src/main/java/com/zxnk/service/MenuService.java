package com.zxnk.service;

import com.zxnk.entity.Menu;

import java.util.List;

public interface MenuService {
    List<String> getPermsByUserId(Long Id);

    //通过用户id获取当前用户的可用路由菜单
    List<Menu> getRouterMenuTreeByUserId(Long userId);
}
