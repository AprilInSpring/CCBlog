package com.zxnk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TagVo
 * @Description TODO
 * @Author cc
 * @Date 2023/5/9 16:58
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagVo {
    //id
    private Long id;
    //name
    private String name;
}