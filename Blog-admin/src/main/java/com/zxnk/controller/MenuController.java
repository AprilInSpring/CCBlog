package com.zxnk.controller;

import com.zxnk.entity.Menu;
import com.zxnk.service.MenuService;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName MenuController
 * @Description TODO
 * @Author cc
 * @Date 2023/5/10 10:07
 * @Version 1.0
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * @param status 菜单状态,默认为可用状态
     * @param menuName 菜单名
     * @return: com.zxnk.util.ResponseResult
     * @decription 查询查询数据对菜单数据进行查询
     * @date 2023/5/10 10:17
    */
    @GetMapping("/list")
    public ResponseResult getMenuList(@RequestParam(defaultValue = "0") String status, String menuName){
        return menuService.selectList(status,menuName);
    }

    /**
     * @param menu 菜单对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 新增菜单对象
     * @date 2023/5/10 10:25
    */
    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    /**
     * @param id 菜单id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据菜单id查询菜单对象
     * @date 2023/5/10 10:29
    */
    @GetMapping("/{id}")
    public ResponseResult selectMenuById(@PathVariable Long id){
        return menuService.selectById(id);
    }

    /**
     * @param menu 菜单对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 完成菜单对象的修改
     * @date 2023/5/10 10:31
    */
    @PutMapping()
    public ResponseResult updateMenuById(@RequestBody Menu menu){
        return menuService.updateById(menu);
    }

    /**
     * @param menuId 菜单id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据菜单id进行逻辑删除
     * @date 2023/5/10 10:36
    */
    @DeleteMapping("/{menuId}")
    public ResponseResult deleteById(@PathVariable Long menuId){
        return menuService.deleteById(menuId);
    }

    /**
     * @return: com.zxnk.util.ResponseResult
     * @decription 查询菜单树，包含层级关系
     * @date 2023/5/10 15:10
    */
    @GetMapping("/treeselect")
    public ResponseResult selectTree(){
        return menuService.selectTree();
    }

    /**
     * @param id 角色id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据角色id获取其对应的权限
     * @date 2023/5/10 16:01
    */
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult selectTreeByRoleId(@PathVariable Long id){
        return menuService.selectTreeByRoleId(id);
    }
}