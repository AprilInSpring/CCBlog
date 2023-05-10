package com.zxnk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxnk.entity.Tag;
import org.springframework.stereotype.Repository;

//文章标签接口
@Repository
public interface TagMapper extends BaseMapper<Tag> {
}
