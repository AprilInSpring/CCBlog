package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.dto.PageVo;
import com.zxnk.dto.TagListDto;
import com.zxnk.dto.TagVo;
import com.zxnk.entity.Tag;
import com.zxnk.mapper.TagMapper;
import com.zxnk.service.TagService;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName TagServiceImpl
 * @Description 标签接口实现类
 * @Author cc
 * @Date 2023/5/9 13:24
 * @Version 1.0
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    /**
     * @param pageNum       分页页码
     * @param pageSize      分页大小
     * @param tagListDto    标签查询参数对象
     * @return: com.zxnk.util.ResponseResult
     * @decription
     * @date 2023/5/9 13:39
    */
    @Override
    public ResponseResult selectPageByDto(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        //构建查询条件
        wrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        wrapper.like(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        //分页查询
        Page<Tag> tagPage = tagMapper.selectPage(new Page<Tag>(pageNum, pageSize), wrapper);
        //返回对象
        return ResponseResult.okResult(new PageVo(tagPage.getRecords(),tagPage.getTotal()));
    }

    /**
     * @param tagListDto 前端分页条件数据封装类
     * @return: com.zxnk.util.ResponseResult
     * @decription 添加标签
     * @date 2023/5/9 13:52
    */
    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        tagMapper.insert(tag);
        return ResponseResult.okResult();
    }

    /**
     * @param id 标签id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据标签id逻辑删除标签
     * @date 2023/5/9 13:58
    */
    @Override
    public ResponseResult deleteTag(Long id) {
        tagMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    /**
     * @param tag 标签数据对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据标签对象数据完成标签对象的更新
     * @date 2023/5/9 16:10
    */
    @Override
    public ResponseResult updateTag(Tag tag) {
        tagMapper.updateById(tag);
        return ResponseResult.okResult();
    }

    /**
     * @param id 标签id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据标签id获取标签对象
     * @date 2023/5/9 16:11
    */
    @Override
    public ResponseResult findById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return ResponseResult.okResult(tag);
    }

    /**
     * @return: com.zxnk.util.ResponseResult
     * @decription 查询所有可用的标签
     * @date 2023/5/9 16:51
    */
    @Override
    public ResponseResult findAll() {
        List<Tag> tags = tagMapper.selectList(null);
        List<TagVo> tagVos = BeanCopyUtils.copyBeans(tags, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }

}