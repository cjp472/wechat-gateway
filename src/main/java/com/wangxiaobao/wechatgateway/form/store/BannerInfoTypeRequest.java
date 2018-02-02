package com.wangxiaobao.wechatgateway.form.store;

import javax.validation.constraints.Min;

import lombok.Data;
@Data
public class BannerInfoTypeRequest {
	@Min(value=1,message="类型必填")
	private int type;
}
