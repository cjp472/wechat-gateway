package com.wangxiaobao.wechatgateway.form.xiaochengxu;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MiniProgramGetCategoryRequest {

	/**
	 * 模板Id
	 */
	@NotEmpty(message="商家小程序Appid必填")
	private String wxAppid;
}
