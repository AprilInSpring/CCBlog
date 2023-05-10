package com.zxnk.dto;

import com.zxnk.entity.Role;
import com.zxnk.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName UserRoleVo
 * @Description TODO
 * @Author cc
 * @Date 2023/5/10 18:21
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleVo {
    private List<Long> roleIds;
    private List<Role> roles;
    private User user;
}