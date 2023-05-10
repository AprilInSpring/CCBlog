package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.dto.PageVo;
import com.zxnk.dto.UserRoleVo;
import com.zxnk.entity.Role;
import com.zxnk.entity.User;
import com.zxnk.dto.UserInfoVo;
import com.zxnk.exception.SystemException;
import com.zxnk.mapper.UserMapper;
import com.zxnk.service.RoleService;
import com.zxnk.service.UserService;
import com.zxnk.util.AppHttpCodeEnum;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.ResponseResult;
import com.zxnk.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
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

    @Autowired
    private RoleService roleService;

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

    @Override
    public ResponseResult selectAll(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        //构建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        //模糊查询名称
        wrapper.like(StringUtils.hasText(userName),User::getUserName,userName);
        //精确查询手机号
        wrapper.eq(StringUtils.hasText(phonenumber),User::getPhonenumber,phonenumber);
        //精确查询状态
        wrapper.eq(StringUtils.hasText(status),User::getStatus,status);
        //分页查询
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return ResponseResult.okResult(new PageVo(userPage.getRecords(),userPage.getTotal()));
    }

    //完成用户新增
    @Override
    @Transactional
    public ResponseResult addUser(User user) {
        //进行注册用户的数据校验
        String userName = user.getUserName();
        //username
        if(!useNameIsIllegal(userName)){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //phone
        if(!phoneNumIsIllegal(user.getPhonenumber())){
            throw new SystemException(500,"手机号已存在");
        }
        if(!emailIsIllegal(user.getEmail())){
            throw new SystemException(500,"邮箱已存在");
        }
        //进行密码加密
        user.setPassword(encoder.encode(user.getPassword()));
        //新增用户，完成主键回填
        userMapper.insert(user);
        //新增用户角色数据
        List<Long> roleIds = user.getRoleIds();
        for (Long roleId : roleIds) {
            userMapper.insertUserRole(user.getId(),roleId);
        }
        return ResponseResult.okResult();
    }

    //逻辑删除用户
    @Override
    @Transactional
    public ResponseResult deleteUserById(Long id) {
        //先删除用户的用户角色数据
        userMapper.deleteUserRole(id);
        //逻辑删除用户
        userMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    //获取用户数据
    @Override
    public ResponseResult getUserById(Long id) {
        //查询用户
        User user = userMapper.selectById(id);
        //查询用户角色数据
        List<Long> userRole = userMapper.getUserRole(id);
        //查询全部角色
        List<Role> roles = (List<Role>) roleService.getAllRole().getData();
        return ResponseResult.okResult(new UserRoleVo(userRole,roles,user));
    }

    //完成用户更新
    @Override
    @Transactional
    public ResponseResult updateUserById(User user) {
        //先新增用户
        userMapper.updateById(user);
        //再删除用户对应的角色关系
        userMapper.deleteUserRole(user.getId());
        //最后添加用户角色关系
        List<Long> roleIds = user.getRoleIds();
        roleIds.forEach(roleId -> userMapper.insertUserRole(user.getId(),roleId));
        return ResponseResult.okResult();
    }

    //更改用户状态
    @Override
    public ResponseResult changeStatus(User user) {
        userMapper.updateById(user);
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

    //判断手机号是否合法
    private boolean phoneNumIsIllegal(String phonenumber){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhonenumber,phonenumber);
        return Objects.isNull(userMapper.selectOne(wrapper));
    }

    //判断昵称是否合法
    private boolean emailIsIllegal(String email){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail,email);
        return Objects.isNull(userMapper.selectOne(wrapper));
    }
}