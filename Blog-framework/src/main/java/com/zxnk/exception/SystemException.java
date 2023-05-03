package com.zxnk.exception;

import com.zxnk.util.AppHttpCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//自定义系统异常类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
    
}