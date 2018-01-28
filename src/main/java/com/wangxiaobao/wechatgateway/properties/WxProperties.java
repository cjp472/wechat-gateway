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
	private String wx_send_kefu_message_url;
	private String wx_template_message_send_url;
	//获取模板库某个模板标题下关键词库
	private String wx_template_library_get_url;
	//小程序游戏中奖模板id
	private String miniprogram_jigsaw_template_message_id;
	//获取已设置的二维码规则
	private String wx_qrcode_qrcodejumpget_url;
	//公众号获取accesstoken
	private String wx_get_access_token_url;
	//增加或修改二维码规则
	private String wx_qrcode_qrcodejumpadd_url;
	//获取二维码校验文件名称及内容
	private String wx_qrcode_qrcodejumpdownload_url;
	//删除二维码规则
	private String wx_qrcode_qrcodejumpdelete_url;
	//发布已设置的二维码规则
	private String wx_qrcode_qrcodejumppublish_url;
}
