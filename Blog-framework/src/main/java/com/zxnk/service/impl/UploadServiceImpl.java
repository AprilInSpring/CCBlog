package com.zxnk.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.zxnk.exception.SystemException;
import com.zxnk.service.UploadService;
import com.zxnk.util.AppHttpCodeEnum;
import com.zxnk.util.PathUtils;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @ClassName UploadServiceImpl
 * @Description 图片上传服务
 * @Author cc
 * @Date 2023/5/6 20:15
 * @Version 1.0
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Value("${oss.accessKey}")
    private String accessKey;

    @Value("${oss.secretKey}")
    private String secretKey;

    @Value("${oss.bucket}")
    private String bucket;

    @Value("${oss.url}")
    private String url;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //获取源文件名
        String originalFilename = img.getOriginalFilename();
        //判断文件后缀，目前只支持jpg和png
        if(!(originalFilename.endsWith("jpg")||originalFilename.endsWith("png"))){
            throw new SystemException(AppHttpCodeEnum.IMG_TYPE_ERROR);
        }
        String imgPath = PathUtils.generateFilePath(originalFilename);
        //进行图片上传到oss，并返回url地址
        String imgUrl = uploadToOOS(img, imgPath);
        return ResponseResult.okResult(imgUrl);
    }

    private String uploadToOOS(MultipartFile img,String filePath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;

        try {
            InputStream inputStream = img.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return url+key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return null;
    }
}