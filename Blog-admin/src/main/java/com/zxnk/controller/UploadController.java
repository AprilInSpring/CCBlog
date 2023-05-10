package com.zxnk.controller;

import com.zxnk.exception.SystemException;
import com.zxnk.service.UploadService;
import com.zxnk.util.AppHttpCodeEnum;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadController
 * @Description 上传控制类
 * @Author cc
 * @Date 2023/5/9 16:53
 * @Version 1.0
 */
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){
        try {
            //上传图片
            return uploadService.uploadImg(img);
        }catch (Exception exception){
            //上传失败
            throw new SystemException(AppHttpCodeEnum.UPLOAD_ERROR);
        }

    }
}