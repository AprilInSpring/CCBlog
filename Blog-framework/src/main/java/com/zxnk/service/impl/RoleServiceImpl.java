package com.zxnk.service.impl;

import com.zxnk.mapper.RoleMapper;
import com.zxnk.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}