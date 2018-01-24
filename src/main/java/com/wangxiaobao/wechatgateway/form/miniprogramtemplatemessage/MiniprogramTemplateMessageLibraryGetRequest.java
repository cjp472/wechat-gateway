package com.wangxiaobao.wechatgateway.form.miniprogramtemplatemessage;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MiniprogramTemplateMessageLibraryGetRequest {
	@NotEmpty(message="模板id必传")
	private String id;
	@NotEmpty(message="商户小程序appId必填")
	private String wxAppid;
	
}
