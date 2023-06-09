package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxnk.entity.LoginUser;
import com.zxnk.entity.User;
import com.zxnk.mapper.MenuMapper;
import com.zxnk.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName UserDetailsServiceImpl
 * @Description SpringSecurity登录认证处理类
 * @Author cc
 * @Date 2023/5/3 11:09
 * @Version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //判断参数是否为空
        if(!StringUtils.hasText(username)){
            throw new RuntimeException("用户名为空");
        }
        //根据用户名进行用户对象查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(wrapper);
        //判断是否查询到相应对象
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        //返回用户信息，只有管理员才有资格进行excel下载
        if(user.getType().equals(1)){   //管理员
            //查询相关权限，并进行数据封装
            List<String> perms = menuMapper.getPermsByUserId(user.getId());
            return new LoginUser(user,perms);
        }

        return new LoginUser(user,null);
    }
}