package com.wangxiaobao.wechatgateway.form.xiaochengxu;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MiniProgramCommitRequest {

	/**
	 * 模板Id
	 */
	@NotEmpty(message="模板Id必填")
	private String templateId;
	@NotEmpty(message="商家小程序Appid必填")
	private String wxAppid;
	@NotEmpty(message="平台id必填")
	private String organizeId;
}
