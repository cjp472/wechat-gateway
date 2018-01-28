package com.wangxiaobao.wechatgateway.form.miniprogramqrcode;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MiniprogramQrCodeRequest {
	@NotEmpty(message="wxAppid必填")
	private String wxAppid;
}
