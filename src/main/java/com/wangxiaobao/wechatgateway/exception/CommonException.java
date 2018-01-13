package com.wangxiaobao.wechatgateway.exception;


import com.wangxiaobao.wechatgateway.enums.ResultEnum;

public class CommonException extends RuntimeException{

    private Integer code;

    public CommonException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public CommonException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
