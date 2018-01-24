package com.wangxiaobao.wechatgateway.form.xiaochengxu;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MiniProgramBindTesterRequest {

	/**
	 * 模板Id
	 */
	@NotEmpty(message="体验者微信号必填")
	private String wechatid;
	@NotEmpty(message="小程序appId必填")
	private String wxAppid;
}
