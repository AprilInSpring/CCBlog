package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.dto.PageVo;
import com.zxnk.entity.Link;
import com.zxnk.dto.LinkVo;
import com.zxnk.mapper.LinkMapper;
import com.zxnk.service.LinkService;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.ResponseResult;
import com.zxnk.util.SystemConstant;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName LinkServiceImpl
 * @Description TODO
 * @Author cc
 * @Date 2023/4/26 23:21
 * @Version 1.0
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    /**
     * @return: java.util.List<com.zxnk.entity.LinkVo>
     * @decription 查询所有已经经过审批的友链数据，进行数据处理并返回
     * @date 2023/4/26 23:28
    */
    @Override
    public List<LinkVo> findAll() {
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Link::getStatus, SystemConstant.LINK_STATUS_NORMAL);
        List<Link> linkList = linkMapper.selectList(wrapper);
        List<LinkVo> linkVoList = BeanCopyUtils.copyBeans(linkList, LinkVo.class);
        return linkVoList;
    }

    //查询所有友链
    @Override
    public ResponseResult getAll(Integer pageNum, Integer pageSize, String name, String status) {
        //构建查询数据
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status),Link::getStatus,status);
        wrapper.like(StringUtils.hasText(name),Link::getName,name);
        //分页查询
        Page<Link> linkPage = linkMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return ResponseResult.okResult(new PageVo(linkPage.getRecords(),linkPage.getTotal()));
    }

    //新增友链
    @Override
    public ResponseResult addLink(Link link) {
        linkMapper.insert(link);
        return ResponseResult.okResult();
    }

    //根据id获取友链
    @Override
    public ResponseResult getById(Long id) {
        return ResponseResult.okResult(linkMapper.selectById(id));
    }

    //更新友链数据
    @Override
    public ResponseResult updateById(Link link) {
        linkMapper.updateById(link);
        return ResponseResult.okResult();
    }

    //根据id删除友链
    @Override
    public ResponseResult deleteById(Long id) {
        linkMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    //修改友链状态
    @Override
    public ResponseResult changeStatus(Link link) {
        linkMapper.updateById(link);
        return ResponseResult.okResult();
    }
}