package com.wangxiaobao.wechatgateway.form.openplatform;

import lombok.Data;

@Data
public class WXopenPlatformMerchantInfoSearchCondition{
	private String wxOpenPlatformId;
	private String wxAppid;
	private String componentVerifyTicket;
	private String authoriceAccessToken;
	private String authoriceRefreshToken;
	private String createUser;
	private java.util.Date createDate;
	private String updateUser;
	private Integer updateDate;
	//公众号创建的开放平台的appId
	private String openAppid;
	//授权账号类型：1：公众号；2小程序
	private String authType;
	//商户品牌id
	private String organizationAccount;
	public WXopenPlatformMerchantInfoSearchCondition(String authType, String organizationAccount) {
		super();
		this.authType = authType;
		this.organizationAccount = organizationAccount;
	}
	public WXopenPlatformMerchantInfoSearchCondition() {
		super();
	}
	
}
