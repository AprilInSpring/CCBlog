package com.zxnk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (CcAudience)表实体类
 * 浏览记录表
 * @author makejava
 * @since 2023-05-26 20:22:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Audience{
    //请求ip

    private String ip;
    //主键id

    private Integer id;

    //归属地
    private String address;

    //时间
    private Date date;
}

