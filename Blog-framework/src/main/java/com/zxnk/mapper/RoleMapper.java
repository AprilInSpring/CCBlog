package com.zxnk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxnk.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {
    List<String> getRoleKeysByUserId(Long id);

    void insertIntoRoleMenu(@Param("id") Long id,@Param("menuId") Long menuId);

    //根据角色id删除全部的角色权限数据
    void deleteByRoleId(Long roleId);
}
