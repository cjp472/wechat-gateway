package com.wangxiaobao.wechatgateway.form.xiaochengxu;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MiniProgramGetAuditstatusRequest {

	/**
	 * 
	 */
	@NotEmpty(message="商家小程序审核Id必填")
	private String auditid;
	@NotEmpty(message="商家小程序wxAppid必填")
	private String wxAppid;
}
