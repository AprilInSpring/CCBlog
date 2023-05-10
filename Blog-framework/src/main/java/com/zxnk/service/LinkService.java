package com.zxnk.service;

import com.zxnk.dto.LinkVo;
import com.zxnk.entity.Link;
import com.zxnk.util.ResponseResult;

import java.util.List;

public interface LinkService {
    List<LinkVo> findAll();

    ResponseResult getAll(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(Link link);

    ResponseResult getById(Long id);

    ResponseResult updateById(Link link);

    ResponseResult deleteById(Long id);

    ResponseResult changeStatus(Link link);
}
