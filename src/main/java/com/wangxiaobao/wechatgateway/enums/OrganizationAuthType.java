package com.wangxiaobao.wechatgateway.enums;
public enum OrganizationAuthType {
	GONGZHONGHAOAUTH("1","公众号授权"),MINIPROGRAMAUTH("2","小程序授权");
	private String type;
	private String name;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private OrganizationAuthType(String type, String name) {
		this.type = type;
		this.name = name;
	}
}
