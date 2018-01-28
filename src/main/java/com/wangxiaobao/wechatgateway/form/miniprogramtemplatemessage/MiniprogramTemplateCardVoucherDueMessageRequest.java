package com.wangxiaobao.wechatgateway.form.miniprogramtemplatemessage;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MiniprogramTemplateCardVoucherDueMessageRequest {
	@NotEmpty(message="发送用户的openId必填")
	private String touser;
	private String formId;
	@NotEmpty(message="奖品名称必填")
	private String rewardName;
	@NotEmpty(message="卡券类别必填")
	private String rewardType;
	@NotEmpty(message="过期时间必填")
	private String endDate;
	@NotEmpty(message="适用门店必填")
	private String merchantName;
	private String remark;
	private String page;
	@NotEmpty(message="小程序appId不能为空")
	private String appId;
}
