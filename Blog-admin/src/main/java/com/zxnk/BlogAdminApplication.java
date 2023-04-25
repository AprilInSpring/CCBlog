package com.zxnk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName BlogAdminApplication
 * @Description TODO
 * @Author cc
 * @Date 2023/4/25 10:45
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.zxnk.mapper")
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class,args);
        System.out.println("博客前台项目启动成功");
    }
}