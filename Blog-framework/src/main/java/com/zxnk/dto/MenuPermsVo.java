package com.zxnk.dto;

import com.zxnk.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName MenuPermsVo
 * @Description 菜单权限类，用于展示前台修改权限时的数据展示
 * @Author cc
 * @Date 2023/5/10 16:30
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuPermsVo {

    private List<Menu> menus;
    private List<Long> checkedKeys;
}