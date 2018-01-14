package com.wangxiaobao.wechatgateway.entity.base;

import lombok.Data;

/**
 * Created by huhuaiyong on 2018/1/10.
 */
@Data
public class PreAuthDto {
    private String pre_auth_code;
    private int expires_in;
}
