package com.zxnk.service;

import com.zxnk.dto.TagListDto;
import com.zxnk.entity.Tag;
import com.zxnk.util.ResponseResult;

public interface TagService {
    ResponseResult selectPageByDto(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult deleteTag(Long id);

    ResponseResult updateTag(Tag tag);

    ResponseResult findById(Long id);

    ResponseResult findAll();
}
