package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.entity.BrowseLog;
import com.zxnk.mapper.BrowseLogMapper;
import com.zxnk.service.BrowseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName BrowseLogImpl
 * @Description 浏览日志服务类
 * @Author cc
 * @Date 2023/6/27 13:23
 * @Version 1.0
 */
@Service
public class BrowseLogImpl implements BrowseLogService {

    @Autowired
    private BrowseLogMapper browseLogMapper;

    @Override
    public List<BrowseLog> selectAll() {
        return browseLogMapper.selectList(null);
    }

    @Override
    public void insert(BrowseLog browseLog) {
        browseLogMapper.insert(browseLog);
    }

    @Override
    public List<BrowseLog> selectPage(int page, int size, String logName) {
        //日志类型非空时，进行条件查询
        LambdaQueryWrapper<BrowseLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(logName),BrowseLog::getLogName,logName);
        Page<BrowseLog> logPage = new Page<>(page, size);
        Page<BrowseLog> browseLogPage = browseLogMapper.selectPage(logPage, wrapper);
        return browseLogPage.getRecords();
    }
}