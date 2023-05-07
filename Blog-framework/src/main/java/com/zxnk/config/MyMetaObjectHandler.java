package com.zxnk.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zxnk.util.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

//mybatisPLus自动填充工具
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * @param metaObject 数据对象
     * @return: void
     * @decription 当数据对象进行新增的时候，完成相应字段的自动填充
     * @date 2023/5/5 14:57
    */
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        //这里逻辑不是很清楚，评论只有登录之后才可以进行，只要登录了，SecurityContext中就会存在用户信息
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            userId = -1L;//表示是自己创建
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy",userId , metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    /**
     * @param metaObject 数据对象
     * @return: void
     * @decription 当数据对象完成修改的时候，完成相应字段的自动填充
     * @date 2023/5/5 15:01
    */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", SecurityUtils.getUserId(), metaObject);
    }
}