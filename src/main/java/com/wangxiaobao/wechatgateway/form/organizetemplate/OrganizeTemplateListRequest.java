package com.wangxiaobao.wechatgateway.form.organizetemplate;

import lombok.Data;

@Data
public class OrganizeTemplateListRequest {

	private String orgName;
	private int page = 10;
	private int size = 1;
	private String isOnline;
	private String status;
	private String notStatus;
}
