package com.wangxiaobao.wechatgateway.form.store;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
@Data
public class BannerInfoTypeRequest {
	@NotEmpty(message="品牌account必填")
	private String orgAccount;
	@Min(value=1,message="类型必填")
	private int type;
}
