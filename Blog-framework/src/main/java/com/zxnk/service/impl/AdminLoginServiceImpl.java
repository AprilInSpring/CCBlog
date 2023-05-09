package com.zxnk.service.impl;

import com.zxnk.dto.AdminUserInfoVo;
import com.zxnk.dto.UserInfoVo;
import com.zxnk.entity.LoginUser;
import com.zxnk.entity.User;
import com.zxnk.service.AdminLoginService;
import com.zxnk.service.MenuService;
import com.zxnk.service.RoleService;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.JwtUtil;
import com.zxnk.util.ResponseResult;
import com.zxnk.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AdminLoginServiceImpl
 * @Description 管理员登录服务接口实现类
 * @Author cc
 * @Date 2023/5/8 9:58
 * @Version 1.0
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult adminLogin(User user) {
        //登录权证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //认证信息，该方法会调用userDetailService的认证逻辑，进行判断，此方法会使用UserDetailService进行逻辑判断
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //获取认证用户信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //认证通过，生成token
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);
        //把用户id和用户信息存入redis，过期时间为10分钟
        redisTemplate.opsForValue().set("loginAdmin:"+userId,loginUser,10, TimeUnit.MINUTES);
        //把token返回
        HashMap<String, String> map = new HashMap<>();
        map.put("token",token);
        return ResponseResult.okResult(map);
    }

    /**
     * @param user 登录用户信息
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据用户信息完成用户权限和角色的封装，并返回
     * @date 2023/5/8 22:14
    */
    @Override
    public ResponseResult getUserInfo(User user){
        Long id = user.getId();
        //查询相应的权限
        List<String> perms = menuService.getPermsByUserId(id);
        //查询相应的角色
        List<String> roleKeys = roleService.getRoleKeysByUserId(id);
        //完成数据封装并返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(
                AdminUserInfoVo.builder()
                        .permissions(perms).roles(roleKeys).user(userInfoVo)
                        .build());
    }
}