package com.zxnk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxnk.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {
    List<String> getRoleKeysByUserId(Long id);
}
