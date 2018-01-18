package com.wangxiaobao.wechatgateway.enums;

import lombok.Getter;

@Getter
public enum OrganizeTemplateStatusEnum {
	UPLOAD("1","已上传"),AUDITING("2","审核中"),SUCCESS("3","审核通过"),FAIL("4","审核未通过"),CANCEL("-1","已取消");
	private String status;
	private String name;
	OrganizeTemplateStatusEnum(String type, String name) {
		this.status = status;
		this.name = name;
	}
}
