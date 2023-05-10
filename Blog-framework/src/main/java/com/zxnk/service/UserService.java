package com.zxnk.service;

import com.zxnk.entity.User;
import com.zxnk.util.ResponseResult;

//用户服务
public interface UserService {

    //获取用户信息
    ResponseResult getUserInfo();

    ResponseResult updateUser(User user);

    ResponseResult register(User user);

    ResponseResult selectAll(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addUser(User user);

    ResponseResult deleteUserById(Long id);

    ResponseResult getUserById(Long id);

    ResponseResult updateUserById(User user);

    ResponseResult changeStatus(User user);
}
