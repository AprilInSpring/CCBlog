package com.zxnk.service;

import com.zxnk.dto.BlogUserLoginVo;
import com.zxnk.entity.User;
import com.zxnk.util.ResponseResult;

public interface UserLoginService {
    //登录认证
    ResponseResult<BlogUserLoginVo> login(User user);
}
