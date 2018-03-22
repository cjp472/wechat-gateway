package com.wangxiaobao.wechatgateway.form.platformminiprogram;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class PlatformMiniprogramSaveRequest {
	@NotEmpty(message="appId必填")
	private String appId;
	@NotEmpty(message="appSecret必填")
	private String appSecret;
	@NotEmpty(message="小程序名称必填")
	private String name;
	@NotEmpty(message="小程序类型必填")
	private String type;
	private int code;
}
