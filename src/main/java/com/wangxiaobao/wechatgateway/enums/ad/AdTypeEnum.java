package com.wangxiaobao.wechatgateway.enums.ad;

import com.wangxiaobao.wechatgateway.enums.CodeEnum;
import lombok.Getter;

@Getter
public enum AdTypeEnum implements CodeEnum {
    PICTURE(0, "图片"),
    VIDEO(1, "视频"),
    ;

    private int code;

    private String message;

    AdTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
