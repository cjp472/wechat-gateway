package com.wangxiaobao.wechatgateway.form.constantcode;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class ConstantCodeWXAuthRequest {
	@NotEmpty(message="type必填")
	private String type;
	@NotEmpty(message="constantKey必填")
	private String constantKey;
}
