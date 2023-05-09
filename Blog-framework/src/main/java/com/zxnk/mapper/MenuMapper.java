package com.zxnk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxnk.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> getPermsByUserId(Long Id);

    List<String> getAllPermissions();
}
