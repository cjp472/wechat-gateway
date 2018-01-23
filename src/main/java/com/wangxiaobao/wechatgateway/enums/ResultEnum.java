package com.wangxiaobao.wechatgateway.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),

    PARAM_ERROR(1, "参数不正确"),
    RETURN_ERROR(2, "返回异常"),

    HEADER_GAIN_ERROR(2,"获取header失败"),
    STORE_NOT_FOUNTD(4,"门店未找到"),
    ACCOUNT_IS_NULL(5,"账号为空"),
    BRAND_NOT_FOUND(6,"未获取品牌账号"),
    MERCHANTACCOUT_INVALID(7,"绑定的账号不在品牌的商家列表中"),
    NO_MORE_TABLECARD(8,"绑定的账号下无桌牌，禁止绑定账号"),

    UNKNOW_ERROR(100, "未知错误"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
