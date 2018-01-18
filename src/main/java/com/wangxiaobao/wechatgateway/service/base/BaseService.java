package com.wangxiaobao.wechatgateway.service.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.wangxiaobao.wechatgateway.properties.WxProperties;

public class BaseService {

	@Autowired
	public WxProperties wxProperties;
}
