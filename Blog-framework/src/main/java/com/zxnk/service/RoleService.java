package com.zxnk.service;

import com.zxnk.entity.Role;
import com.zxnk.util.ResponseResult;

import java.util.List;

public interface RoleService {
    List<String> getRoleKeysByUserId(Long id);

    ResponseResult selectPage(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(Long roleId, String status);

    ResponseResult addRole(Role role);

    ResponseResult getRoleById(Long id);

    ResponseResult updateRoleById(Role role);

    ResponseResult deleteById(Long id);

    ResponseResult getAllRole();
}
