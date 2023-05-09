package com.zxnk.controller;

import com.zxnk.service.UploadService;
import com.zxnk.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadController
 * @Description 图片上传控制器
 * @Author cc
 * @Date 2023/5/6 20:13
 * @Version 1.0
 */
@RestController
@Api(tags = "图片上传控制类",description = "图片上传相应接口")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation(value = "进行图片上传",notes = "图片暂时仅支持jpg和png两种格式")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}