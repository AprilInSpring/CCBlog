package com.zxnk.service.impl;

import com.zxnk.mapper.MenuMapper;
import com.zxnk.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MenuServiceImpl
 * @Description TODO
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
        if(id == 1L){
            List<String> allPermissions = menuMapper.getAllPermissions();
            return allPermissions;
        }
        return menuMapper.getPermsByUserId(id);
    }
}