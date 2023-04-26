package com.zxnk.service;

import com.zxnk.dao.Link;
import com.zxnk.entity.LinkVo;

import java.util.List;

public interface LinkService {
    List<LinkVo> findAll();
}
