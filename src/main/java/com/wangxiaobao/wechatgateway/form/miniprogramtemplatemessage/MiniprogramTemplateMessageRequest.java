package com.wangxiaobao.wechatgateway.form.miniprogramtemplatemessage;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MiniprogramTemplateMessageRequest {
	@NotEmpty(message="发送用户的appid必填")
	private String touser;
	private String formId;
	@NotEmpty(message="奖品名称必填")
	private String rewardName;
	@NotEmpty(message="状态必填")
	private String status;
	@NotEmpty(message="商家名称必填")
	private String merchantName;
	private String remark;
	private String page;
}
