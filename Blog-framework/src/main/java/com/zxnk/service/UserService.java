package com.zxnk.service;

import com.zxnk.dao.User;
import com.zxnk.util.ResponseResult;

//用户服务
public interface UserService {

    //获取用户信息
    ResponseResult getUserInfo();

    ResponseResult updateUser(User user);

    ResponseResult register(User user);
}
