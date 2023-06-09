package com.zxnk;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.zxnk.entity.ArticleTag;
import com.zxnk.entity.BrowseLog;
import com.zxnk.mapper.ArticleTagMapper;
import com.zxnk.mapper.BrowseLogMapper;
import com.zxnk.util.CommonIpAddressUtil;
import com.zxnk.util.MailUtils;
import org.apache.commons.math3.util.RandomPivotingStrategy;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
public class OSSTest {

    @Autowired
    private BrowseLogMapper browseLogMapper;

    @Test
    public void test(){
        /*String cityInfo = CommonIpAddressUtil.getCityInfo("115.215.118.96");
        System.out.println(cityInfo);*/
        List<BrowseLog> browseLogs = browseLogMapper.selectList(null);
        browseLogs.toString();
    }
}