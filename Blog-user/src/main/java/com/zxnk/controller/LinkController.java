package com.zxnk.controller;

import com.zxnk.dto.LinkVo;
import com.zxnk.service.LinkService;
import com.zxnk.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName LinkController
 * @Description TODO
 * @Author cc
 * @Date 2023/4/26 23:25
 * @Version 1.0
 */
@RestController
@RequestMapping("/link")
@Api(tags = "友链控制类",description = "友链相应接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * @return: com.zxnk.util.ResponseResult<java.util.List<com.zxnk.entity.LinkVo>>
     * @decription 查询所有已经经过审批的友链数据，进行数据处理并返回
     * @date 2023/4/26 23:29
    */
    @GetMapping("/getAllLink")
    @ApiOperation("查询所有已经经过审批的友链数据，进行数据处理并返回")
    public ResponseResult<List<LinkVo>> getAllLink(){
        List<LinkVo> linkVoList = linkService.findAll();
        return ResponseResult.okResult(linkVoList);
    }
}