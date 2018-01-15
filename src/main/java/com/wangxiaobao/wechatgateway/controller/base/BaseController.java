package com.wangxiaobao.wechatgateway.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import com.wangxiaobao.wechatgateway.properties.WxProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BaseController {
	@Autowired
	public WxProperties wxProperties;
	@Autowired
	public RestTemplate restTemplate;
	@Value("${wechat.openplatform.appid}")
	public String appId;
	@Value("${wechat.openplatform.appsecret}")
	public String appsecret;
}
