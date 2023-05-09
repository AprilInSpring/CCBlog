package com.zxnk.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 评论表(CcComment)表实体类
 *
 * @author makejava
 * @since 2023-05-04 20:31:51
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "评论表(CcComment)表实体类")
public class Comment{

    private Long id;
    @ApiModelProperty(notes = "评论类型（0代表文章评论，1代表友链评论）")
    //评论类型（0代表文章评论，1代表友链评论）
    private String type;
    @ApiModelProperty(notes = "文章id")
    //文章id
    private Long articleId;
    @ApiModelProperty(notes = "根评论id")
    //根评论id
    private Long rootId;
    @ApiModelProperty(notes = "评论内容")
    //评论内容
    private String content;
    @ApiModelProperty(notes = "所回复的目标评论的userid")
    //所回复的目标评论的userid
    private Long toCommentUserId;
    @ApiModelProperty(notes = "所回复的目标评论的userid")
    //回复目标评论id
    private Long toCommentId;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

}

