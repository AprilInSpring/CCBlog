package com.zxnk.service.impl;

import com.zxnk.dto.AdminUserInfoVo;
import com.zxnk.dto.UserInfoVo;
import com.zxnk.entity.LoginUser;
import com.zxnk.entity.Menu;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
        //把用户id和用户信息存入redis，过期时间为1小时
        redisTemplate.opsForValue().set("loginAdmin:"+userId,loginUser,1, TimeUnit.HOURS);
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


    /**
     * @param userId 用户id
     * @return: java.util.List<com.zxnk.entity.Menu>
     * @decription 根据用户id获取当前用户可用的菜单树
     * @date 2023/5/9 10:52
    */
    @Override
    public List<Menu> getRouterMenuTreeByUserId(Long userId) {
        return menuService.getRouterMenuTreeByUserId(userId);
    }

    /**
     * @return: com.zxnk.util.ResponseResult
     * @decription 退出登录，并清除redis和SecurityContext中的身份信息
     * @date 2023/5/9 10:56
    */
    @Override
    public ResponseResult logout() {
        //获取用户id
        Long userId = SecurityUtils.getUserId();
        //清除redis中的用户信息
        redisTemplate.delete("loginAdmin:"+userId);
        //清除上下文的用户信息
        SecurityContextHolder.clearContext();
        return ResponseResult.okResult();
    }
}