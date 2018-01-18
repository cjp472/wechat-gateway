package com.wangxiaobao.wechatgateway.form.xiaochengxu;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MiniProgramSetweappsupportversionRequest {

	/**
	 * 模板Id
	 */
	@NotEmpty(message="最低基础库版本必填")
	private String version;
	@NotEmpty(message="商家小程序Appid必填")
	private String wxAppid;
}
