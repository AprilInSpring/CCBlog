package com.zxnk.service;

import com.zxnk.entity.Menu;
import com.zxnk.util.ResponseResult;

import java.util.List;

public interface MenuService {
    List<String> getPermsByUserId(Long Id);

    //通过用户id获取当前用户的可用路由菜单
    List<Menu> getRouterMenuTreeByUserId(Long userId);

    ResponseResult selectList(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult selectById(Long id);

    ResponseResult updateById(Menu menu);

    ResponseResult deleteById(Long menuId);

    ResponseResult selectTree();

    ResponseResult selectTreeByRoleId(Long id);
}
