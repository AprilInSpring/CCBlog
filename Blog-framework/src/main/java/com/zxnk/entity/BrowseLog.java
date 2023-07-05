package com.zxnk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName BrowseLog
 * @Description 浏览首页日志对象
 * @Author cc
 * @Date 2023/6/27 11:55
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrowseLog {

    private Long id;            //id

    private String ip;          //ip

    private String addr;        //ip归属地

    private Date localTime;     //浏览时间

    private String os;          //操作系统

    private String browser;     //浏览器类型

    private String logName;     //日志类型

    private String methodName;  //方法名称

    private String className;   //请求类名

    private String url;         //请求url
}