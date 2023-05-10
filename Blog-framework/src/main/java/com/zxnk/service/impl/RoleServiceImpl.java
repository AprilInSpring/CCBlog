package com.zxnk.service.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.x.protobuf.Mysqlx;
import com.zxnk.dto.PageVo;
import com.zxnk.entity.Role;
import com.zxnk.mapper.RoleMapper;
import com.zxnk.service.RoleService;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author cc
 * @Date 2023/5/8 21:49
 * @Version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * @param id 用户id
     * @return: java.util.List<java.lang.String>
     * @decription 根据用户id查询用户的角色名
     * @date 2023/5/8 22:05
    */
    @Override
    public List<String> getRoleKeysByUserId(Long id) {
        return roleMapper.getRoleKeysByUserId(id);
    }

    /**
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param roleName 角色名称
     * @param status 角色状态
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据分页数据和查询条件对角色进行分页查询
     * @date 2023/5/10 14:46
    */
    @Override
    public ResponseResult selectPage(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        //对名称进行模糊查询
        wrapper.like(StringUtils.hasText(roleName),Role::getRoleName,roleName);
        //对状态进行查询
        wrapper.eq(StringUtils.hasText(status),Role::getStatus,status);
        //对role_sort进行升序排序
        wrapper.orderByAsc(Role::getRoleSort);
        //分页查询
        Page<Role> rolePage = roleMapper.selectPage(new Page<Role>(pageNum, pageSize), wrapper);
        return ResponseResult.okResult(new PageVo(rolePage.getRecords(),rolePage.getTotal()));
    }

    /**
     * @param roleId 角色id
     * @param status 角色状态
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据角色id改变角色状态
     * @date 2023/5/10 14:56
    */
    @Override
    public ResponseResult changeStatus(Long roleId, String status) {
        roleMapper.updateById(Role.builder().id(roleId).status(status).build());
        return ResponseResult.okResult();
    }

    /**
     * @param role 角色对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 完成角色对象的新增，并且维护角色权限表
     * @date 2023/5/10 15:42
    */
    @Override
    @Transactional
    public ResponseResult addRole(Role role) {
        //先新增，拿到回填id
        roleMapper.insert(role);
        //拿到角色权限表数据
        Long id = role.getId();
        List<Long> menuIds = role.getMenuIds();
        //再维护角色权限表
        menuIds.forEach(menuId -> roleMapper.insertIntoRoleMenu(id,menuId));
        return ResponseResult.okResult();
    }

    /**
     * @param id 角色id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据角色id获取角色数据
     * @date 2023/5/10 15:59
    */
    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = roleMapper.selectById(id);
        return ResponseResult.okResult(role);
    }

    /**
     * @param role 角色对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据角色对象完成角色数据的修改
     * @date 2023/5/10 16:34
    */
    @Override
    @Transactional
    public ResponseResult updateRoleById(Role role) {
        //完成角色修改
        roleMapper.updateById(role);
        //删除所关联的角色权限数据
        roleMapper.deleteByRoleId(role.getId());
        //新增角色权限数据
        List<Long> menuIds = role.getMenuIds();
        menuIds.stream().forEach(menuId -> roleMapper.insertIntoRoleMenu(role.getId(),menuId));
        return ResponseResult.okResult();
    }

    /**
     * @param id 角色id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据角色id逻辑删除角色对象
     * @date 2023/5/10 16:41
    */
    @Override
    @Transactional
    public ResponseResult deleteById(Long id) {
        //删除角儿权限数据
        roleMapper.deleteByRoleId(id);
        //逻辑删除角色
        roleMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    //查询所有状态正常的角色
    @Override
    public ResponseResult getAllRole() {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getStatus,"0");
        List<Role> roles = roleMapper.selectList(wrapper);
        return ResponseResult.okResult(roles);
    }
}