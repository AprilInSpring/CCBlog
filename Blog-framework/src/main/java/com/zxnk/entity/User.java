package com.zxnk.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户表(CcUser)表实体类
 *
 * @author makejava
 * @since 2023-05-02 22:19:53
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户表(CcUser)表实体类")
public class User {
    //主键
    private Long id;
    //用户名
    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String userName;
    //昵称
    @NotNull(message = "昵称不能为空")
    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty("昵称")
    private String nickName;
    //密码
    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;
    //用户类型：0代表普通用户，1代表管理员
    @ApiModelProperty("用户类型：0代表普通用户，1代表管理员")
    private String type;
    //账号状态（0正常 1停用）
    @ApiModelProperty("账号状态（0正常 1停用）")
    private String status;
    //邮箱
    @NotNull(message = "邮箱不能为空")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不合法")
    @ApiModelProperty("邮箱")
    private String email;
    //手机号
    @ApiModelProperty("手机号")
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    @ApiModelProperty("用户性别（0男，1女，2未知）")
    private String sex;
    //头像
    @ApiModelProperty("头像")
    private String avatar;
    //创建人的用户id
    private Long createBy;
    //创建时间
    private Date createTime;
    //更新人
    private Long updateBy;
    //更新时间
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

}

