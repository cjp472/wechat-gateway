package com.wangxiaobao.wechatgateway.form.openplatform;

import lombok.Data;

@Data
public class WXopenPlatformMerchantInfoResponse {
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
	private String organizeId;
	//第三方平台appid
	private String componentAppid;
	//第三方平台accessToken
	private String componentAccessToken;
}
