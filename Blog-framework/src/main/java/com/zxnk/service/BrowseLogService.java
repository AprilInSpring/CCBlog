package com.zxnk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxnk.entity.BrowseLog;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @ClassName browseLogService
 * @Description TODO
 * @Author cc
 * @Date 2023/6/27 13:20
 * @Version 1.0
 */
public interface BrowseLogService {
    //查询所有日志
    public List<BrowseLog> selectAll();
    //新增日志
    public void insert(BrowseLog browseLog);
    //分页多条件查询日志
    public List<BrowseLog> selectPage(int page,int size,String logName);
}