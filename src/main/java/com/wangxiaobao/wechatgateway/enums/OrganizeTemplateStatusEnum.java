package com.wangxiaobao.wechatgateway.enums;

import lombok.Getter;

@Getter
public enum OrganizeTemplateStatusEnum {
	UPLOAD("1", "已上传"), AUDITING("2", "审核中"), SUCCESS("3", "审核通过"), FAIL("4", "审核未通过"),ONLINE("5","已发布"), CANCEL("-1", "已取消");
	private String status;
	private String name;

	OrganizeTemplateStatusEnum(String status, String name) {
		this.status = status;
		this.name = name;
	}

	private OrganizeTemplateStatusEnum() {
	}

	public static OrganizeTemplateStatusEnum getStatusByWeixinStatus(int wxStatus) {
		String wxstatus = wxStatus + "";
		OrganizeTemplateStatusEnum organizeTemplateStatusEnum = null;
		switch (wxstatus) {
		case "0":
			return organizeTemplateStatusEnum=SUCCESS;
		case "1":
			return organizeTemplateStatusEnum=FAIL;
		case "2":
			return organizeTemplateStatusEnum=AUDITING;
		}
		return organizeTemplateStatusEnum;
	}

}
