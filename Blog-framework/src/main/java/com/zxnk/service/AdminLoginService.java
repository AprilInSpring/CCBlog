package com.zxnk.service;

import com.zxnk.entity.User;
import com.zxnk.util.ResponseResult;

//管理员登录服务接口
public interface AdminLoginService {
    ResponseResult adminLogin(User user);
    public ResponseResult getUserInfo(User user);
}
