package com.zxnk.controller;

import com.zxnk.entity.Role;
import com.zxnk.service.RoleService;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author cc
 * @Date 2023/5/10 14:41
 * @Version 1.0
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param roleName 角色名称
     * @param status 角色状态
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据分页数据和查询条件对角色进行分页查询
     * @date 2023/5/10 14:45
    */
    @GetMapping("/list")
    public ResponseResult getRoleList(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10")Integer pageSize,
                                      String roleName,
                                      String status){
    return roleService.selectPage(pageNum,pageSize,roleName,status);
    }

    /**
     * @param role 角色对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据角色对象，完成状态修改
     * @date 2023/5/10 15:07
    */
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody Role role){
        return roleService.changeStatus(role.getId(),role.getStatus());
    }

    /**
     * @param role 角色对象数据
     * @return: com.zxnk.util.ResponseResult
     * @decription 完成角色数据的新增，并且维护相应的角色权限表
     * @date 2023/5/10 15:41
    */
    @PostMapping()
    public ResponseResult addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

    /**
     * @param id 角色id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据角色id获取角色数据
     * @date 2023/5/10 15:58
    */
    @GetMapping("/{id}")
    public ResponseResult getRoleById(@PathVariable Long id){
        return roleService.getRoleById(id);
    }

    /**
     * @param role 角色对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据角色对象完成角色数据的修改
     * @date 2023/5/10 16:34
    */
    @PutMapping()
    public ResponseResult updateRoleById(@RequestBody Role role){
        return roleService.updateRoleById(role);
    }

    /**
     * @param id 角色id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据角色id逻辑删除角色数据
     * @date 2023/5/10 16:41
    */
    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return roleService.deleteById(id);
    }

    /**
     * @return: com.zxnk.util.ResponseResult
     * @decription 获取所有状态正常的角色数据
     * @date 2023/5/10 16:56
    */
    @GetMapping("/listAllRole")
    public ResponseResult getAllRole(){
        return roleService.getAllRole();
    }
}