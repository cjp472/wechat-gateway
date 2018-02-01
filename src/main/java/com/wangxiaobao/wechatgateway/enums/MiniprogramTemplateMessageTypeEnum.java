package com.wangxiaobao.wechatgateway.enums;

public enum MiniprogramTemplateMessageTypeEnum {
	ACTIVITYRESULTNOTIFY(1,"商家活动结果通知"),CARDTIMEOUTNOFITY(2,"卡券到期提醒通知");
	private int type;
	private String name;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private MiniprogramTemplateMessageTypeEnum(int type, String name) {
		this.type = type;
		this.name = name;
	}
}
