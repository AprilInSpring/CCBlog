package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxnk.dao.Link;
import com.zxnk.entity.LinkVo;
import com.zxnk.mapper.LinkMapper;
import com.zxnk.service.LinkService;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}