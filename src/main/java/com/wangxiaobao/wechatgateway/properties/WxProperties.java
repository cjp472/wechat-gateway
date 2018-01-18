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
	private String wx_miniprogram_commit_url;
	private String wx_miniprogram_get_category_url;
	private String wx_miniprogram_submit_audit_url;
	private String wx_miniprogram_get_page_url;
	private String wx_miniprogram_get_auditstatus_url;
	private String wx_miniprogram_get_latest_auditstatus_url;
	private String wx_miniprogram_release_url;
	private String wx_miniprogram_setweappsupportversion_url;
}
