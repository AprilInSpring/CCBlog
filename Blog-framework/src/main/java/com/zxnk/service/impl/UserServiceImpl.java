package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxnk.dao.User;
import com.zxnk.entity.UserInfoVo;
import com.zxnk.exception.SystemException;
import com.zxnk.mapper.UserMapper;
import com.zxnk.service.UserService;
import com.zxnk.util.AppHttpCodeEnum;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.ResponseResult;
import com.zxnk.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @ClassName UserServiceImpl
 * @Description 用户服务实现类
 * @Author cc
 * @Date 2023/5/6 18:02
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder encoder;

    //查询用户信息
    @Override
    public ResponseResult getUserInfo() {
        //获取用户
        Long userId = SecurityUtils.getUserId();
        User user = userMapper.selectById(userId);
        //完成用户数据的封装
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    //完成用户的数据更新
    @Override
    public ResponseResult updateUser(User user) {
        userMapper.updateById(user);
        return ResponseResult.okResult();
    }

    //完成用户注册
    @Override
    public ResponseResult register(User user) {
        //进行注册用户的数据校验
        String userName = user.getUserName();
        if(!useNameIsIllegal(userName)){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        String nickName = user.getNickName();
        if(!nickNameIsIllegal(nickName)){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXISTED);
        }
        //进行密码加密
        user.setPassword(encoder.encode(user.getPassword()));
        userMapper.insert(user);
        return ResponseResult.okResult();
    }

    //判断用户名是否合法
    private boolean useNameIsIllegal(String userName){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,userName);
        return Objects.isNull(userMapper.selectOne(wrapper));
    }

    //判断昵称是否合法
    private boolean nickNameIsIllegal(String nickName){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNickName,nickName);
        return Objects.isNull(userMapper.selectOne(wrapper));
    }
}