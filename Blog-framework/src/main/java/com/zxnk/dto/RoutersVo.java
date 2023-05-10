package com.zxnk.dto;

import com.zxnk.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName RoutersVo
 * @Description 前端路由信息实体类
 * @Author cc
 * @Date 2023/5/9 10:03
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoutersVo {

    private List<Menu> menus;
}