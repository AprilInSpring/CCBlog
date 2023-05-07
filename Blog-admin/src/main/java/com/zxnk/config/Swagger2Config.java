package com.zxnk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @ClassName Swagger2Config
 * @Description swagger2配置类
 * @Author cc
 * @Date 2023/5/7 20:39
 * @Version 1.0
 */
@Configuration
public class Swagger2Config {

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zxnk.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("ccg", "http://8.142.134.180", "ccgccg@yeah.net");
        return new ApiInfoBuilder()
                .title("博客系统")
                .description("以下是博客系统的开发文档")
                .contact(contact)   // 联系方式
                .version("1.0.0")  // 版本
                .build();
    }
}