package com.zxnk;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName Test
 * @Description TODO
 * @Author cc
 * @Date 2023/5/9 21:51
 * @Version 1.0
 */
@SpringBootTest
@ConfigurationProperties(prefix = "oss")
@Data
public class TestBug {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String url;

    @Test
    public void doSome(){
        System.out.println(url);
    }
}