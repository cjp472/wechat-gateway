package com.wangxiaobao.wechatgateway.form.store;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
@Data
public class BannerInfoRequest {
	@NotEmpty(message="品牌account必填")
	private String orgAccount;
}
