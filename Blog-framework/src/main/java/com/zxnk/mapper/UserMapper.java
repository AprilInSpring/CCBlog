package com.zxnk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxnk.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    void insertUserRole(@Param("userId") Long userId,@Param("roleId") Long roleId);

    void deleteUserRole(Long userId);

    List<Long> getUserRole(Long id);
}
