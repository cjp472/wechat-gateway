package com.wangxiaobao.wechatgateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Configuration
@ConfigurationProperties(ignoreInvalidFields=false)
@PropertySource("classpath:wx.properties")
@Data
@Component
public class WxProperties {
	private String token;
	private String encodingAesKey;
	private String wx_modify_domain_url;
	private String wx_setwebviewdomain_url;
}
