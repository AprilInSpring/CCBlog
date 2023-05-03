package com.zxnk.service;

import com.zxnk.entity.BlogUserLoginVo;
import com.zxnk.dao.User;
import com.zxnk.util.ResponseResult;

public interface BlogLoginService {
    //登录认证
    ResponseResult<BlogUserLoginVo> login(User user);
}
