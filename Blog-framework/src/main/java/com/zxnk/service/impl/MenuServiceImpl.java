package com.zxnk.service.impl;

import com.zxnk.entity.Menu;
import com.zxnk.mapper.MenuMapper;
import com.zxnk.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName MenuServiceImpl
 * @Description 后台菜单路由管理
 * @Author cc
 * @Date 2023/5/8 21:47
 * @Version 1.0
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * @param id 用户id
     * @return: java.util.List<java.lang.String>
     * @decription 根据用户id查询所有的权限
     * @date 2023/5/8 22:07
    */
    @Override
    public List<String> getPermsByUserId(Long id) {
        //若用户id为1，则代表管理员，直接返回全部权限
        if(id.equals(1L)){
            List<String> allPermissions = menuMapper.getAllPermissions();
            return allPermissions;
        }
        return menuMapper.getPermsByUserId(id);
    }

    //根据用户id查询当前用户可用的路由菜单，如果是超级管理员(id为1)，则直接返回全部可用的路由菜单
    @Override
    public List<Menu> getRouterMenuTreeByUserId(Long userId) {
        //新建路由菜单集合
        List<Menu> menus = null;
        if(userId.equals(1L)){
            //当前用户为超级管理员
            menus = menuMapper.getAllRouterMenu();
        }else {
            //根据用户id进行查询
            menus = menuMapper.getRouterMenuTreeByUserId(userId);
        }
        //完成子路由菜单的封装
        List<Menu> menuTree = buildMenuTree(menus);
        return menuTree;
    }

    /**
     * @param menus 可用路由菜单信息
     * @return: java.util.List<com.zxnk.entity.Menu>
     * @decription 从可用路由菜单中完成路由菜单树的构建
     * @date 2023/5/9 10:46
    */
    private List<Menu> buildMenuTree(List<Menu> menus){
        List<Menu> menuList = menus.stream()
                //1.过滤出顶层菜单
                .filter(menu -> menu.getParentId().equals(0L))
                //完成子路由菜单的封装
                .map(menu -> menu.setChildren(setChildren(menu.getId(),menus)))
                .collect(Collectors.toList());
        return menuList;
    }
    /**
     * @param id   父级菜单id
     * @param menus 可用的菜单集合
     * @return: java.util.List<com.zxnk.entity.Menu>
     * @decription  根据父级菜单id，从可用的菜单集合中封装子菜单数据到父级菜单
     * @date 2023/5/9 10:40
    */
    private List<Menu> setChildren(Long id,List<Menu> menus){
        List<Menu> menuChildrenList = menus.stream()
                //过滤出该父级菜单下的子路由菜单
                .filter(menu -> menu.getParentId().equals(id))
                //继续递归，完成多层级的封装，一般三级递归
                .map(menu -> menu.setChildren(setChildren(menu.getId(), menus)))
                .collect(Collectors.toList());
        return menuChildrenList;
    }
}