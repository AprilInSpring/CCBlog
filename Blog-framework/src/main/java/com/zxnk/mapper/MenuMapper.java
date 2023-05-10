package com.zxnk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxnk.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    //根据用户id查询该用户可用的权限
    List<String> getPermsByUserId(Long Id);

    //查询所有可用的权限
    List<String> getAllPermissions();

    //查询所有可用的路由菜单(管理员用户)
    List<Menu> getAllRouterMenu();

    //查询当前用户可用的路由菜单
    List<Menu> getRouterMenuTreeByUserId(Long userId);

    //根据角色id查询所有关联的权限id
    List<Long> getPermsByRoleId(Long roleId);
}
