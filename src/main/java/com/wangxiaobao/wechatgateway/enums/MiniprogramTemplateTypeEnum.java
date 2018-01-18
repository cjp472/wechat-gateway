package com.wangxiaobao.wechatgateway.enums;

import lombok.Getter;

@Getter
public enum MiniprogramTemplateTypeEnum {
	PLATFORM_PAGE_TEMPLATE("1","平台黄页小程序"),PLATFORM_TALK_TEMPLATE("2","包你说");
	private String type;
	private String name;
	MiniprogramTemplateTypeEnum(String type, String name) {
		this.type = type;
		this.name = name;
	}
}
