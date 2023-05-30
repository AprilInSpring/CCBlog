package com.zxnk.service.impl;

import com.zxnk.entity.Audience;
import com.zxnk.mapper.AudienceMapper;
import com.zxnk.service.AudienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AudienceServiceImpl
 * @Description TODO
 * @Author cc
 * @Date 2023/5/26 20:25
 * @Version 1.0
 */
@Service
public class AudienceServiceImpl implements AudienceService {

    @Autowired
    private AudienceMapper audienceMapper;


    /**
     * @return: java.util.List<com.zxnk.entity.Audience>
     * @decription 查询所有的浏览记录
     * @date 2023/5/26 20:26
    */
    @Override
    public List<Audience> findAll() {
        return audienceMapper.selectList(null);
    }

    /**
     * @param audiences 浏览记录集合
     * @return: void
     * @decription 根据浏览记录集合完成数据的更新
     * @date 2023/5/26 20:41
    */
    @Override
    public void updateAudience(List<Audience> audiences) {
        //完成数据插入
        audiences.forEach(audience -> {
            audienceMapper.insert(audience);
        });
    }
}