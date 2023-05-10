package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxnk.dto.MenuPermsVo;
import com.zxnk.entity.Menu;
import com.zxnk.mapper.MenuMapper;
import com.zxnk.service.MenuService;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
     * @param status 菜单状态
     * @param menuName 菜单名
     * @return: com.zxnk.util.ResponseResult
     * @decription 查询查询数据对菜单数据进行查询
     * @date 2023/5/10 10:17
    */
    @Override
    public ResponseResult selectList(String status, String menuName) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getStatus,status);
        wrapper.like(StringUtils.hasText(menuName),Menu::getMenuName,menuName);
        wrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = menuMapper.selectList(wrapper);
        return ResponseResult.okResult(menus);
    }

    /**
     * @param menu 菜单对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 新增菜单对象
     * @date 2023/5/10 10:25
    */
    @Override
    public ResponseResult addMenu(Menu menu) {
        menuMapper.insert(menu);
        return ResponseResult.okResult();
    }

    /**
     * @param id 菜单id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据菜单id查询菜单对象
     * @date 2023/5/10 10:29
    */
    @Override
    public ResponseResult selectById(Long id) {
        return ResponseResult.okResult(menuMapper.selectById(id));
    }

    /**
     * @param menu 菜单对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 完成菜单对象的修改
     * @date 2023/5/10 10:32
    */
    @Override
    public ResponseResult updateById(Menu menu) {
        //进行逻辑判断，父级菜单不可以设为自己，如果设置了给出相应提示，并修改失败
        if(menu.getParentId().equals(menu.getId())){
            return ResponseResult.errorResult(500,"修改菜单'写博文'失败，上级菜单不能选择自己");
        }
        menuMapper.updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteById(Long menuId) {
        //逻辑判断，当前菜单菜单存在子菜单，则无法删除，并返回报错信息
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId,menuId);
        List<Menu> menus = menuMapper.selectList(wrapper);
        if(menus != null && menus.size() > 0){
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }else {
            menuMapper.deleteById(menuId);
            return ResponseResult.okResult();
        }
    }

    /**
     * @return: com.zxnk.util.ResponseResult
     * @decription 查询菜单树，包含层级关系
     * @date 2023/5/10 15:09
    */
    @Override
    public ResponseResult selectTree() {
        List<Menu> menuTree = buildTree();
        //完成数据转换
        return ResponseResult.okResult(menuTree);
    }

    /**
     * @return: java.util.List<com.zxnk.entity.Menu>
     * @decription 查询所有的菜单树，并完成子菜单的封装
     * @date 2023/5/10 16:28
    */
    private List<Menu> buildTree(){
        //查询所有可用菜单数据
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getStatus,"0");
        wrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = menuMapper.selectList(wrapper);
        menus.forEach(menu -> menu.setLabel(menu.getMenuName()));
        //进行子菜单的拼接
        List<Menu> menuTree = this.buildMenuTree(menus);
        return menuTree;
    }
    /**
     * @param id 角色id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据角色id查询角色的权限集合
     * @date 2023/5/10 16:02
    */
    @Override
    public ResponseResult selectTreeByRoleId(Long id) {
        //获取菜单树
        List<Menu> menus = buildTree();
        //获取用户的权限id
        List<Long> perms = menuMapper.getPermsByRoleId(id);
        //完成数据封装
        MenuPermsVo menuPermsVo = new MenuPermsVo(menus, perms);
        return ResponseResult.okResult(menuPermsVo);
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