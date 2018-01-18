package com.wangxiaobao.wechatgateway.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),

    PARAM_ERROR(1, "参数不正确"),
    RETURN_ERROR(2, "返回异常"),

    UNKNOW_ERROR(100, "未知错误"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
