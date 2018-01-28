package com.wangxiaobao.wechatgateway.form.miniprogramqrcode;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MiniprogramQrCodeDeleteRequest {
	@NotEmpty(message="wxAppid必填")
	private String wxAppid;
	
	@NotEmpty(message="二维码规则必填")
	private String prefix;
}
