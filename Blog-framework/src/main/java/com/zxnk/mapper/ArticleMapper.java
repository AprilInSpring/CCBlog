package com.zxnk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxnk.entity.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    //返回所有状态正常文章的类别id，用于前端做分类查询
    List<Long> getAllCategoryId();
}
